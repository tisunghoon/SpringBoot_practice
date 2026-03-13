package com.example.board.repository;

import com.example.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 게시글 Repository
 *
 * JpaRepository<Entity, PK타입>을 상속받으면 기본 CRUD 메서드 자동 제공
 * - save(): 저장/수정
 * - findById(): ID로 조회
 * - findAll(): 전체 조회
 * - delete(): 삭제
 * - count(): 개수 조회
 *
 * Spring Data JPA가 인터페이스를 보고 구현체를 자동 생성 (프록시)
 *
 * 쿼리 메서드:
 * - 메서드 이름으로 쿼리 생성 (findBy, countBy 등)
 * - 예: findByAuthor(String author) -> SELECT * FROM posts WHERE author = ?
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 페이징 처리된 게시글 목록 조회 (생성일 기준 내림차순)
     *
     * Pageable: 페이징 정보 (page, size, sort)
     * Page<T>: 페이징 결과 (content, totalElements, totalPages 등)
     */
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * 제목으로 검색 (페이징)
     * containing: LIKE '%keyword%'
     */
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    /**
     * 작성자로 검색 (페이징)
     */
    Page<Post> findByAuthor(String author, Pageable pageable);

    /**
     * JPQL을 사용한 커스텀 쿼리
     *
     * @Query: 직접 JPQL 작성
     * JPQL은 엔티티 객체를 대상으로 쿼리 (SQL은 테이블 대상)
     *
     * :keyword - 파라미터 바인딩
     * @Param: 파라미터 이름 매핑
     */
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    Page<Post> searchByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);
}
