package com.example.board.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger (OpenAPI 3.0) 설정 클래스
 *
 * Swagger란?
 * - REST API 문서를 자동으로 생성해주는 도구
 * - API를 테스트할 수 있는 UI 제공
 *
 * @Bean: 이 메서드가 반환하는 객체를 스프링 IoC 컨테이너에 Bean으로 등록
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("게시판 API")
                        .description("스프링 부트 학습용 게시판 CRUD API")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Developer")
                                .email("developer@example.com")));
    }
}
