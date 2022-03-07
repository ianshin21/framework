package com.kh.mybatis.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

@DisplayName("Board 테스트")
class BoardServiceTest {
	private BoardService service;
	
	@BeforeEach
	void setUp() {
		service = new BoardService();
	}

	@Test
	void nothing() {
		
	}
	
	@Test
	void create() {
		assertThat(service).isNotNull();
	}

	@ParameterizedTest
	@CsvSource(
		value = {
		" admin, NIL, NIL",
		" NIL, 이거, NIL",
		" NIL, NIL, 빨리 자자",
		" NIL, NIL, NIL"
	}, nullValues = "NIL")       // 빈공간을 두거나, nullValues = "NIL" 이렇게 특정 문자를 null 공간대신에 사용하겠다고 지정
	void getBoardList(String writer, String title, String content) {		
		List<Board> list = null;
		
		list =service.getBoardList(writer, title, content);   // 생성자와 상관 없나?  member에서 같은 코드 확인요? 
																// 값을 저장하는 것이 아니라 찾아오는 것이므로 생성자와 상관없음
		System.out.println(list.size());
		System.out.println(list);
		
		assertThat(list).isNotNull();
		assertThat(list.size()).isGreaterThan(0);
	}
	
	@ParameterizedTest
	@MethodSource("listProvider")
	void getBoardList(int currentPage, List<String> filters) {		
		
		System.out.println(filters);       // 실행하기 전에 테스트용 
			        // 페이징 처리 전 (List<String> filters) 후 (int currentPage, List<String> filters)  어떻게 달라지는지 확인요
		
		List<Board> list = null;
		
		int listCount = service.getBoardCount(filters);
		
		PageInfo info = new PageInfo(currentPage, 10, listCount, 10);
		
		list =service.getBoardList(info, filters);
		
		System.out.println(list.size());
		System.out.println(list);
		System.out.println(info);
		
		assertThat(list).isNotNull();
		assertThat(list.size()).isPositive().isLessThanOrEqualTo(10);
		
		/*
		 * 아래와 같이도 해 봤음
		 * 	void getBoardList(int expected, List<String> filters) {	
		 * 
		 * 	assertThat(list.size()).isPositive().isEqualTo(expected);	
		 * 
		 *  Arguments.arguments(210, Arrays.asList("B1")),	숫자는 미리 확인한 게시글 수이다. 다를 수 있음
			Arguments.arguments(3, Arrays.asList("B2")),	
			Arguments.arguments(2, Arrays.asList("B3")),	
			Arguments.arguments(5, Arrays.asList("B2", "B3"))	
		 * 
		 */
		
	}
	
	@ParameterizedTest
	@ValueSource(ints = {21, 142, 201})
	void getBoardDetail(int boardNo) {
//		int boardNo = 21    // @ParameterizedTest 로 작업하기 전 @Test로 작업했을 때
		
		Board board = service.getBoardDetail(boardNo);		// 1대 n의 관계 : 1)과 2)를 합쳐서 가져온다. 
		
//		1) Board board = service.getBoardDetail(boardNo);		// 기존에 하던 방식 
//		2) BoardReply reply = service.getBoardReply(boardNo);
//		3) request.setAttribute("board", board);				// b_backend\BoardViewServlet
//		4) request.setAttribute("reply", reply);
		
		
		System.out.println(board);
		System.out.println(board.getReplies());
		System.out.println(board.getReplies().size());
		
		assertThat(board).isNotNull();
		assertThat(board.getReplies()).isNotNull();
		assertThat(board.getReplies()).size().isGreaterThan(0);
		
	}
	
	static Stream<Arguments> listProvider(){
		return Stream.of(
			Arguments.arguments(1, Arrays.asList("B1")),	// 1은 currentPage
			Arguments.arguments(2, Arrays.asList("B1")),	
			Arguments.arguments(3, Arrays.asList("B1")),	
			Arguments.arguments(4, Arrays.asList("B1")),	
			Arguments.arguments(16, Arrays.asList("B1")),	
			Arguments.arguments(1, Arrays.asList("B2", "B3"))	
		);
		
	}
}
