package com.kh.mybatis;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
public class App {	
	//@Slf4j으로 인해 아래의 선언문은 생략이 가능하다.
	//private static Logger log = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        
        log.info("Hello SLF4J");
        log.debug("Hello SLF4J - d");
    }
}

// 이 화면에서 오른쪽 클릭 Run as java application 으로 돌려보기 : ctrl+F11