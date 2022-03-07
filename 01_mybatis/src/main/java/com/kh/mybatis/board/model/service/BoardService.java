package com.kh.mybatis.board.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kh.mybatis.App;
import com.kh.mybatis.board.model.dao.BoardDao;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

import lombok.extern.slf4j.Slf4j;

import static com.kh.mybatis.common.template.SqlSessionTemplate.*;

@Slf4j
public class BoardService {
	//@Slf4j으로 인해 아래의 선언문은 생략이 가능하다.
	//private static Logger log = LoggerFactory.getLogger(App.class);
	private BoardDao boardDao = new BoardDao();
	
	public List<Board> getBoardList(String writer, String title, String content) {
		log.info("getBoardList() - 호출");
		List<Board> list = null;
		SqlSession session = getSession();
		
		log.info(writer);
		log.info(content);
		
		list = boardDao.getBoardList(session, writer, title, content);
		
		session.close();
		
		return list;
	}

	public int getBoardCount(List<String> filters) {
		log.info("getBoardCount() - 호출");
		int result = 0;
		
		SqlSession session = getSession();
		
		result = boardDao.getBoardCount(session, filters);
		
		session.close();
		
		return result;
	}

	public List<Board> getBoardList(PageInfo info, List<String> filters) {
		log.info("getBoardList() - 호출");
		List<Board> list = null;
		SqlSession session = getSession();
		
		list = boardDao.getBoardList(session, info, filters);
		
		session.close();
		
		return list;
	}

	public Board getBoardDetail(int boardNo) {
		log.info("getBoardDetail() - 호출");
		Board board = null;
		
		SqlSession session = getSession();
		
		board = boardDao.getBoardDetail(session, boardNo);

		session.close();
		
		return board;
	}




}
