package com.example.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing 설정 클래스
 *
 * @Configuration: 이 클래스가 스프링 설정 클래스임을 나타냄
 * @EnableJpaAuditing: JPA Auditing 기능 활성화
 *
 * JPA Auditing이란?
 * - 엔티티의 생성일/수정일을 자동으로 관리해주는 기능
 * - @CreatedDate, @LastModifiedDate 어노테이션과 함께 사용
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
