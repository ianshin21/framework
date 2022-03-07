package com.kh.mybatis.board.model.vo;


import java.sql.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardReply {
	private int replyNO;
	
	private int boardNO;
		
	private String userId;
	
	private String replyContent;	
	
	private String status;
	
	private Date replyCreateDate;
	
	private Date replyModifyDate;


}


