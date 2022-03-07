package com.kh.mybatis.member.model.dao;

import java.util.List;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.vo.Member;

public class MemberDao {

	public int getMemberCount(SqlSession session) {
		
		/*
		 * 마이바틱스를 적용했기 때문에 SqlSesseion 객체가 제공하는 메소드를 통해서 SQL실행 시킨다.
		 * 
		 * 객체 한 개를 조회하기 위해서 SqlSession의 selectOne() 메소드 사용
		 * 	 - 첫번째 매개값은 쿼리문이 존재하는 "매퍼의 네임스페이스.쿼리문아이디."
		 * 		(mybatis-config.xml에 등록된 mapper의 경로대로 탐색한다.)
		 *   - 두번째 매개값은 쿼리문에서 사용될 파라미터 객체
		 */
		
//		return = 1; 로 설정해서 먼저 Test 통과하도록 한 다음 진행하였음 mapper 설정 이전에도 테스트 가능하다. 
		
		return session.selectOne("memberMapper.selectCount");
	}

	public List<Member> findMemberAll(SqlSession session) {
		
		return session.selectList("memberMapper.selectMemberAll");
	}

	public Member findMemberById(SqlSession session, String id) {
		
		return session.selectOne("memberMapper.selectMember", id);
		
		// 처음 생성하면 return = null; 로 되어있다. 그대로 테스트하면 에러남 
		// => return = member; 로 해주고 test 통과시키고 mapper로 이동, 작업 진행
	}

	public int insertMember(SqlSession session, Member member) {
		
		return session.insert("memberMapper.insertMember", member);
	}

	public int updateMember(SqlSession session, Member member) {
		
		return session.update("memberMapper.updateMember", member);
	}

	public int deleteMember(SqlSession session, int userNo) {
		
		return session.delete("memberMapper.deleteMember", userNo);
	}


}
