package com.example.board.repository;

import com.example.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 댓글 Repository
 *
 * 쿼리 메서드 규칙:
 * - findBy + 필드명: 해당 필드로 조회
 * - findBy + 연관엔티티_필드: 연관관계를 통한 조회
 *
 * 예: findByPost_Id(Long postId)
 *     -> SELECT * FROM comments WHERE post_id = ?
 *
 * OrderBy + 필드명 + Desc/Asc: 정렬
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 게시글의 댓글 목록 조회 (생성일 기준 오름차순)
     * Post 엔티티의 id 필드로 조회
     */
    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);

    /**
     * 특정 게시글의 댓글 개수 조회
     */
    Long countByPostId(Long postId);

    /**
     * 특정 게시글의 모든 댓글 삭제
     */
    void deleteByPostId(Long postId);
}
