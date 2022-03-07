package com.kh.mybatis.member.model.vo;

import java.sql.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {


	private int userNo;
	
	private String userId;
	
	private String userPwd;
	
	private String userName;
	
	private String userRole;
	
	private String status;
	
	private Date enrollDate;
	
	private Date modifyDate;
	
	
	public Member(String id, String pwd, String name) {
		this.userId = id;
		this.userPwd = pwd;
		this.userName = name;
	}
	
	
}
