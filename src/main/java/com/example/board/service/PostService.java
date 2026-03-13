package com.example.board.service;

import com.example.board.dto.request.PostRequestDto;
import com.example.board.dto.response.PostListResponseDto;
import com.example.board.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 게시글 서비스 인터페이스
 *
 * 인터페이스를 사용하는 이유:
 * 1. 구현체와 분리하여 유연한 변경 가능 (DIP - 의존성 역전 원칙)
 * 2. 테스트 시 Mock 객체로 대체 용이
 * 3. 다형성 활용 (여러 구현체 교체 가능)
 *
 * 서비스 계층의 역할:
 * - 비즈니스 로직 처리
 * - 트랜잭션 관리
 * - DTO <-> Entity 변환
 */
public interface PostService {

    /**
     * 게시글 작성
     *
     * @param requestDto 게시글 작성 요청 데이터
     * @return 생성된 게시글 정보
     */
    PostResponseDto createPost(PostRequestDto requestDto);

    /**
     * 게시글 목록 조회 (페이징)
     *
     * @param pageable 페이징 정보 (page, size, sort)
     * @return 페이징된 게시글 목록
     */
    Page<PostListResponseDto> getAllPosts(Pageable pageable);

    /**
     * 게시글 단건 조회 (조회수 증가)
     *
     * @param id 게시글 ID
     * @return 게시글 상세 정보 (댓글 포함)
     */
    PostResponseDto getPost(Long id);

    /**
     * 게시글 수정
     *
     * @param id 게시글 ID
     * @param requestDto 수정할 데이터
     * @return 수정된 게시글 정보
     */
    PostResponseDto updatePost(Long id, PostRequestDto requestDto);

    /**
     * 게시글 삭제
     *
     * @param id 게시글 ID
     */
    void deletePost(Long id);

    /**
     * 게시글 검색 (제목/내용)
     *
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 게시글 목록
     */
    Page<PostListResponseDto> searchPosts(String keyword, Pageable pageable);
}
