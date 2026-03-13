package com.example.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 엔티티
 *
 * Post와 다대일(ManyToOne) 관계
 * - 여러 댓글이 하나의 게시글에 속함
 */
@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, length = 50)
    private String author;

    /**
     * @ManyToOne: 다대일 관계 설정 (Comment N : Post 1)
     *
     * - fetch = LAZY: 지연 로딩
     *                 댓글 조회 시 게시글을 바로 가져오지 않음
     *                 실제 post를 사용할 때 쿼리 실행
     *
     * @JoinColumn: 외래키(FK) 매핑
     * - name: FK 컬럼명
     * - nullable: NULL 허용 여부 (댓글은 반드시 게시글에 속해야 함)
     *
     * 연관관계의 주인: 외래키(FK)를 관리하는 쪽
     * Comment가 post_id FK를 가지므로 Comment가 연관관계의 주인
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Comment(String content, String author, Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
    }

    /**
     * 댓글 수정 메서드
     */
    public void update(String content) {
        this.content = content;
    }

    /**
     * 연관관계 설정 메서드
     * Post의 addComment 메서드에서 호출됨
     */
    void setPost(Post post) {
        this.post = post;
    }
}
