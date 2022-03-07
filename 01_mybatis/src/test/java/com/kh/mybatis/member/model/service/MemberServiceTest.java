package com.kh.mybatis.member.model.service;


import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;       //'junit-jupiter-params' dependency에 추가
import org.junit.jupiter.params.provider.CsvSource;	     //comma-separated values 여러 타입
import org.junit.jupiter.params.provider.ValueSource;    // 한 가지 타입만

import com.kh.mybatis.member.model.vo.Member;

@DisplayName("Member 테스트")
@TestMethodOrder(OrderAnnotation.class)
class MemberServiceTest {
	
	private MemberService service;
	
	// 아래의 테스트 메소드들이 실행되기 전에 무조건 실행되는 메소드
	@BeforeEach
	void setup() {
		service = new MemberService();
	}

	@Test
	@Disabled
	void nothing() {
		// 이 테스트를 통해서 현재 프로로젝트가 테스트 가능한 환경인지 확인한다.
	}

	@Test
	@Disabled
	void create() {
		assertThat(service).isNotNull();				
	}
	
	
	/*
	 * ※ 여기서부터 MemberServiceTest에서 시작해서 에러를 수정해가면서 MemberService로 작업을 이어간다.
	 * 
	 * void memberCount() { 
	 * 	  int count = service.getMemberCount();
	 *    assertThat(count).isGreaterThan(0); } 이라고 하고
	 * 
	 * public int getMemberCount() {
	 * 
	 * return 0 } 이렇게 하면 Test 에러가 발생하고 여기서 하나씩 에러 코드를 수정해 나가는 것이 TDD 방식이다.
	 */
	
	
	@Test
	@Order(1)
	void memberCount() {
		int count = service.getMemberCount();
		
		//보통은 출력문을 두지 않는다 이 자체가 테스터를 위한 것이기 때문; 멤버수 리턴 : 현재 4명
//		System.out.println(count);
		
		assertThat(count).isGreaterThan(0);
	}
	
	@Test
	void findMemberAll() {
		List<Member> list = null;
		
		list = service.findMemberAll();
//		System.out.println(list);
		
		assertThat(list).isNotNull();
	}
	
	
	// test 메소드는 항상 void, 반환값 없도록
	@ParameterizedTest       // @Test 대신에 사용 
	@Order(2)
	@ValueSource(strings = {"admin", "anyno"})
	void findMember(String id) {
//		Member member = service.findMemberById(“admin”);   이때는 매개변수 없었다. void findMember()
//		매개 변수를 사용하기 위해서 junit-jupiter-params dependency에 추가 =>@ValueSource 추가  =>  void findMember(String id)
		
		Member member = service.findMemberById(id);
		
		assertThat(member).isNotNull();	
		assertThat(member.getUserId()).isEqualTo(id);	
	}
		
	
	@ParameterizedTest
	@Order(3)
	@CsvSource({
		"test01, 1234, 김민규",
		"test02, 1234, 백장미"
	})
	void saveMember(String id, String pwd, String name) {
		System.out.println("id" + id + "pwd" + pwd + "name" + name);    // Csv 를 실험함
		
		int result = 0;
		Member member = new Member(id, pwd, name);			// vo에 생성자 만들어서 사용(??? 이건 아닌 것 같음).  update와 비교
		
		System.out.println(member);     // userNo = 0 아직 데이터가 넘어가 저장되기 전이므로 No가 결정되지 않았음
		
		result = service.saveMember(member);
		
		System.out.println(member);     // useGeneratedKeys="true" keyProperty="userNo" keyColumn="USER_NO" 확인용
										// 얘들이 없을 때는 userNo가 0으로 넘어오고, 얘들로 인해 DB sequence에 의해 새로 생성된 userNo가 넘어옴.
										// * DB sequence에 의해 새로 생성되는 userNo는 delete한 멤버에 상관없이 계속 연속적으로 생성 
		
		// 실제로 DB에서 Member가 저장되었는지 확인하기 위해 해당 id로 다시 Member를 조회
		member = service.findMemberById(id);
		
		assertThat(result).isGreaterThan(0);
		assertThat(member).isNotNull();
		assertThat(member.getUserId()).isNotNull().isEqualTo(id);    //메소드 체이닝  assertThat에서 제공
	}
	
// spring에서는 @Transaction 어노테이션을 사용하면 Test코드는 실행되고 난 다음 모두 Rollback 된다. 
	
	@ParameterizedTest
	@CsvSource({
		"test01, 4567, 김승현",
		"test02, 4567, 정희재"
	})
	@Order(4)
	void updateMember(String id, String pwd, String name) {

		int result = 0;	     // 영향 받은 행의 개수를 전달하기 때문에 int이다. 
		
		Member member = service.findMemberById(id);   // 먼저 member를 찾아 와야함. 프로젝트에서는 loginMember, 그리고 view에서
		
		member.setUserPwd(pwd);
		member.setUserName(name);
		
		result = service.saveMember(member);
		
		System.out.println(member);
				
		// 실제로 DB에서 Member가 수정되었는지 확인하기 위해 해당 id로 다시 Member를 조회
		member = service.findMemberById(id);
		
		System.out.println(member);
		
		assertThat(result).isGreaterThan(0);
		assertThat(member).isNotNull();
		assertThat(member.getUserPwd()).isNotNull().isEqualTo(pwd);
		assertThat(member.getUserName()).isNotNull().isEqualTo(name);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test01", "test02"})
	@Order(5)
	void deleteMember(String id) {
		int result = 0;
		Member member = service.findMemberById(id);     // 먼저 member를 찾아 와야함. 프로젝트에서는 loginMember, 그리고 view에서
		
		result = service.deleteMember(member.getUserNo());
		
		// 실제로 DB에서 지워졌는지 확인하기 위해 해당 id로 다시 Member를 조회
		member = service.findMemberById(id);
		
		assertThat(result).isGreaterThan(0);
		assertThat(member).isNull();
	}
}

   
