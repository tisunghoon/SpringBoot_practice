package com.example.board.service;

import com.example.board.dto.request.PostRequestDto;
import com.example.board.dto.response.PostListResponseDto;
import com.example.board.dto.response.PostResponseDto;
import com.example.board.entity.Post;
import com.example.board.exception.PostNotFoundException;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 서비스 구현체
 *
 * @Service: 이 클래스가 서비스 계층의 Bean임을 나타냄
 *           스프링 IoC 컨테이너가 이 클래스를 Bean으로 등록
 *
 * @RequiredArgsConstructor: Lombok - final 필드의 생성자 자동 생성
 *                          생성자 주입 방식 (권장)
 *
 * @Transactional: 트랜잭션 관리
 * - readOnly = true: 읽기 전용 트랜잭션 (성능 최적화)
 * - 클래스 레벨에 적용하면 모든 메서드에 적용
 * - 메서드 레벨에서 재정의 가능
 *
 * 트랜잭션이란?
 * - 데이터베이스 작업의 논리적 단위
 * - ACID 특성: 원자성, 일관성, 격리성, 지속성
 * - 여러 작업을 하나로 묶어서 모두 성공하거나 모두 실패
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    /**
     * 생성자 주입 (Constructor Injection)
     *
     * @Autowired를 사용하지 않는 이유:
     * 1. final 키워드로 불변성 보장
     * 2. 테스트 시 의존성 주입 용이
     * 3. 순환 참조 방지
     * 4. 컴파일 시점에 의존성 체크 가능
     */
    private final PostRepository postRepository;

    /**
     * 게시글 작성
     *
     * @Transactional: 쓰기 작업이므로 readOnly = false (기본값)
     * 클래스 레벨의 readOnly = true를 오버라이드
     */
    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto) {
        log.info("Creating post with title: {}", requestDto.getTitle());

        // DTO -> Entity 변환
        Post post = requestDto.toEntity();

        // 저장 (영속화)
        Post savedPost = postRepository.save(post);

        log.info("Post created with id: {}", savedPost.getId());

        // Entity -> DTO 변환 후 반환
        return PostResponseDto.from(savedPost);
    }

    /**
     * 게시글 목록 조회 (페이징)
     *
     * Page<Entity> -> Page<DTO> 변환
     * Page.map() 메서드로 각 요소 변환
     */
    @Override
    public Page<PostListResponseDto> getAllPosts(Pageable pageable) {
        log.info("Getting all posts with pageable: {}", pageable);

        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostListResponseDto::from);
    }

    /**
     * 게시글 단건 조회 (조회수 증가)
     *
     * @Transactional: 조회수 증가(쓰기)가 포함되어 있음
     *
     * orElseThrow: Optional이 비어있으면 예외 발생
     */
    @Override
    @Transactional
    public PostResponseDto getPost(Long id) {
        log.info("Getting post with id: {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        // 조회수 증가 (Dirty Checking으로 자동 업데이트)
        post.increaseViewCount();

        return PostResponseDto.from(post);
    }

    /**
     * 게시글 수정
     *
     * JPA의 Dirty Checking (변경 감지):
     * - 영속성 컨텍스트 내의 엔티티 상태 변화를 감지
     * - 트랜잭션 커밋 시점에 변경된 내용 자동 UPDATE
     * - 별도의 save() 호출 불필요
     */
    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        log.info("Updating post with id: {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        // 엔티티 상태 변경 (Dirty Checking)
        post.update(requestDto.getTitle(), requestDto.getContent());

        log.info("Post updated with id: {}", id);

        return PostResponseDto.from(post);
    }

    /**
     * 게시글 삭제
     *
     * CascadeType.ALL + orphanRemoval = true 설정으로
     * 게시글 삭제 시 관련 댓글도 자동 삭제
     */
    @Override
    @Transactional
    public void deletePost(Long id) {
        log.info("Deleting post with id: {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        postRepository.delete(post);

        log.info("Post deleted with id: {}", id);
    }

    /**
     * 게시글 검색
     */
    @Override
    public Page<PostListResponseDto> searchPosts(String keyword, Pageable pageable) {
        log.info("Searching posts with keyword: {}", keyword);

        return postRepository.searchByTitleOrContent(keyword, pageable)
                .map(PostListResponseDto::from);
    }
}
