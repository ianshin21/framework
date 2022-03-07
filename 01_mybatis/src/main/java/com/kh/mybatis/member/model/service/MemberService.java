package com.kh.mybatis.member.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.dao.MemberDao;
import com.kh.mybatis.member.model.vo.Member;

import static com.kh.mybatis.common.template.SqlSessionTemplate.*;

import java.util.ArrayList;
import java.util.List;

public class MemberService {
	private MemberDao memberDao = new MemberDao();
	// 여기서 MemberDao에 ctrl+1 MemberDao 클래스 생성 
	
	public int getMemberCount() {
		int count = 0;
		SqlSession session = getSession();
//		SqlSession session = SqlSessionTemplate.getSession(); static을 추가하고 *을 추가하여 위와 같이 변경
		
		
		count = memberDao.getMemberCount(session);
		
		session.close();    // session을 닫지 않으면 다른 문제가 발생할 수 있으므로 일단 닫고 다른 메소드에서 다시 호출
		
		return count;
	}

	public List<Member> findMemberAll() {
		List<Member> list = null;
		SqlSession session = getSession();
		
		list = memberDao.findMemberAll(session);
		
		session.close();
		
		return list;
//		return new ArrayList<>;  테스트시  빨간불 없애기 위해 처음 이렇게 시도함
	}
	
	
	public Member findMemberById(String id) {
		Member member = null;	  // 1
		SqlSession session = getSession();   // 3
		
		member = memberDao.findMemberById(session, id);  // 4
		
		session.close();   // 5
		
		return member;   // 2
	}

	public int saveMember(Member member) {
		int result = 0;
		SqlSession session = getSession();
		
		if(member.getUserNo() != 0) {
			result = memberDao.updateMember(session, member);
		}else {
			result = memberDao.insertMember(session, member);
		}
		
		if(result > 0) {
			session.commit();
		}else {
			session.rollback();
		}
		return result;
	}

	public int deleteMember(int userNo) {
		int result = 0;
		SqlSession session = getSession();
		
		result = memberDao.deleteMember(session, userNo);
		
		if(result > 0) {
			session.commit();
		}else {
			session.rollback();
		}
		return result;
	}



}
