package com.kh.mybatis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kh.mybatis.common.template.SqlSessionTemplate;

@DisplayName("첫번째 테스트 코드 작성 ")
public class AppTest {
   private SqlSession session;
   
    @Test
    @DisplayName("SqlSession 생성 테스트")
    public void create()
    {
    	session = SqlSessionTemplate.getSession();
    	
    	assertNotNull(session);
    }
}
