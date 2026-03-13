package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 공통 엔티티 클래스 (생성일/수정일 자동 관리)
 *
 * @MappedSuperclass: 이 클래스를 상속받는 엔티티에 필드를 전달
 *                    테이블과 직접 매핑되지 않음 (상속용 클래스)
 *
 * @EntityListeners: 엔티티의 변화를 감지하는 리스너 등록
 *                   AuditingEntityListener가 생성일/수정일을 자동으로 채워줌
 *
 * @CreatedDate: 엔티티 생성 시 자동으로 현재 시간 저장
 * @LastModifiedDate: 엔티티 수정 시 자동으로 현재 시간 갱신
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
