package com.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 스프링 부트 메인 애플리케이션 클래스
 *
 * @SpringBootApplication 은 다음 3개의 어노테이션을 포함합니다:
 * - @Configuration: 이 클래스가 Bean 설정 클래스임을 나타냄
 * - @EnableAutoConfiguration: 스프링 부트의 자동 설정 활성화
 * - @ComponentScan: 현재 패키지 하위의 컴포넌트들을 스캔
 */
@SpringBootApplication
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }
}
