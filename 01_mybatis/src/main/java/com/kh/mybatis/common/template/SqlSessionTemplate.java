package com.kh.mybatis.common.template;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionTemplate {

	public static SqlSession getSession() {
		SqlSession session = null;
		String resource = "mybatis-config.xml";
		
		
			try {
				InputStream is = Resources.getResourceAsStream(resource);
				SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
				SqlSessionFactory factory = builder.build(is);
				//SqlSessionFactory factory = builder.build(is, "kh");
				
				// true : 오토커밋  / false : 수동커밋
				session = factory.openSession(false);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
				
				
		return session;
		
	}
	
}
