package com.kh.mybatis.board.model.dao;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

public class BoardDao {

	public List<Board> getBoardList(SqlSession session, String writer, String title, String content) {
		
		// 객체를 넘길 때 멤버변수 혹은 컬럼 모두를 넘길 때는 member, board 등으로 하고
		// 이와 같이 값의 일부를 객체로 넘기려 할 때는 map으로 넘긴다. (board 객체로 넘겨도 된다.)
		Map<String, String> map = new HashMap<>();
		
//		map.put("key", value);
		map.put("writer", writer);
		map.put("title", title);
		map.put("content", content);
			
		return session.selectList("boardMapper.selectBoardList", map);
//		return session.selectList("boardMapper.selectBoardList");
		// map 파라미타 없이 넘기면 (그리고 mapper에서도 파라미터 없이 받고, choose 조건문도 없이 일단 테스트. 모두 다 list로 넘어간다. 
	}

	public int getBoardCount(SqlSession session, List<String> filters) {
		Map<String, List<String>> map = new HashMap<>();
		
		map.put("filters", filters);
		
		return session.selectOne("boardMapper.selectBoardCountByFilters", map);
	
	}
	
	public List<Board> getBoardList(SqlSession session, PageInfo info, List<String> filters) {
		
		/*
		 * List 타입이나 Array 타입의 데이터를 파라미터로 전달하면 내부적으로 Map으로 타입이 변환되어 저장되기 때문에 
		 * Mapper에서는 list나 array라는 이름으로 사용해야 한다. 
		 * 
		 * 만약에 filters라는 이름을 Mapper에서 사용하고 싶으면 map에 담아서 파라미터로 전달해야 한다 
		 */
		Map<String, List<String>> map = new HashMap<>();
		
		map.put("filters", filters);
		
		/*
		 * JSP /Servlet 에서는 쿼리문에서 RowNum을 조건을 통해서 페이징 처리를 하였다.
		 * 하지만 마이바티스에서는 페이징 처리를 위해 RowBounds라는 클래스를 제공한다. 
		 * 
		 * RowBounds => offset과 listLimit값을 넘겨 받아서 페이징 처리를 쉽게 구현
		 * 				(offset만큼 건너뛰고 limit 만큼 가져온다.) 
		 * 
		 * offset - 몇 개의 게시글을 건너 뛰고 조회를 할지 계산한다. 
		 * 
		 * offset = 0, Limit = 10
		 *  - 0개를 건너뛰고 10개를 가져온다. 1~10
		 * offset = 10, Limit = 10 
		 *  - 10개를 건너뛰고 10개를 가져온다. 11~20
		 *  
		 *  offset = 20, Limit = 10 
		 *  - 20개를 건너뛰고 10개를 가져온다. 21~30
		 */
		
		int offset = (info.getCurrentPage() - 1) * info.getListLimit();
		RowBounds rowBounds = new RowBounds(offset, info.getListLimit());
		
		return session.selectList("boardMapper.selectBoardListByFilters", map, rowBounds);
		//return session.selectList("boardMapper.selectBoardListByFilters", filters);    map에 담기전  
		//return new ArrayList<>();   // 코드 작성하기전 테스트 통과용
	}

	public Board getBoardDetail(SqlSession session, int boardNo) {
		
		//return new Board();  //test 통과 목적의 임시 구성 mapper 만들기 전 사용
		return session.selectOne("boardMapper.selectBoardDetail", boardNo);
		
	}



}
