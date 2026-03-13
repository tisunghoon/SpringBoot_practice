package com.example.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 엔티티
 *
 * @Entity: 이 클래스가 JPA 엔티티임을 나타냄 (DB 테이블과 매핑)
 * @Table: 매핑할 테이블 이름 지정
 * @Getter: Lombok - 모든 필드의 getter 메서드 자동 생성
 * @NoArgsConstructor: Lombok - 기본 생성자 자동 생성
 *                     JPA는 기본 생성자가 필수 (리플렉션으로 객체 생성)
 *                     AccessLevel.PROTECTED로 외부 생성 제한
 */
@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    /**
     * @Id: 이 필드가 PK(Primary Key)임을 나타냄
     * @GeneratedValue: PK 생성 전략 설정
     *                  IDENTITY: DB에 위임 (MySQL의 AUTO_INCREMENT)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column: 컬럼 매핑 설정
     * - nullable: NULL 허용 여부
     * - length: 문자열 최대 길이 (VARCHAR 길이)
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * @Lob: Large Object
     * - 대용량 데이터 타입 매핑 (TEXT, BLOB 등)
     * - MySQL에서는 LONGTEXT로 매핑됨
     */
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String author;

    /**
     * 조회수 - 기본값 0
     */
    @Column(nullable = false)
    private Integer viewCount = 0;

    /**
     * @OneToMany: 일대다 관계 설정 (Post 1 : Comment N)
     *
     * - mappedBy: 연관관계의 주인을 지정 (Comment의 post 필드)
     *             외래키(FK)를 관리하는 쪽이 연관관계의 주인
     *
     * - cascade: 영속성 전이 설정
     *            CascadeType.ALL: 모든 상태 변화를 자식에게 전파
     *            (저장, 수정, 삭제 등)
     *
     * - orphanRemoval: 고아 객체 제거
     *                  부모와 연결이 끊어진 자식 엔티티 자동 삭제
     *
     * - fetch: 로딩 전략
     *          LAZY: 지연 로딩 (실제 사용 시점에 로딩) - 권장
     *          EAGER: 즉시 로딩 (엔티티 조회 시 함께 로딩)
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    /**
     * @Builder: Lombok - 빌더 패턴 구현
     * 객체 생성 시 가독성 향상 및 필수 값 검증 가능
     */
    @Builder
    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = 0;
    }

    /**
     * 게시글 수정 메서드
     * 엔티티의 상태 변경은 반드시 엔티티 내부 메서드를 통해 수행 (캡슐화)
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 조회수 증가 메서드
     */
    public void increaseViewCount() {
        this.viewCount++;
    }

    /**
     * 댓글 추가 메서드 (양방향 연관관계 편의 메서드)
     */
    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }
}
