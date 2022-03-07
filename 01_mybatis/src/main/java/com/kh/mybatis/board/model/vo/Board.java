package com.kh.mybatis.board.model.vo;

import java.sql.*;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	private int boardNo;
	
	private String userId;
		
	private String boardTitle;
	
	private String boardContent;
	
	private int boardReadCount;
	
	private String status;
	
	private List<BoardReply> replies;    // 객체지향으로 만들기 : 이것이 객체 지향임
	
	private Date boardCreateDate;
	

	

}
