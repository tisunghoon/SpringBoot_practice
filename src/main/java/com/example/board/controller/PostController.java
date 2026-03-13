package com.example.board.controller;

import com.example.board.dto.request.PostRequestDto;
import com.example.board.dto.response.PostListResponseDto;
import com.example.board.dto.response.PostResponseDto;
import com.example.board.exception.ErrorResponse;
import com.example.board.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 REST API 컨트롤러
 *
 * @RestController: @Controller + @ResponseBody
 * - 모든 메서드의 반환값을 JSON으로 직렬화하여 HTTP 응답 본문에 포함
 *
 * @RequestMapping: 공통 URL 경로 지정
 *
 * REST API 설계 원칙:
 * - URL은 리소스를 나타냄 (명사 사용)
 * - HTTP 메서드로 행위를 표현 (GET, POST, PUT, DELETE)
 * - 적절한 HTTP 상태 코드 반환
 *
 * Swagger 어노테이션:
 * @Tag: API 그룹 정보
 * @Operation: API 설명
 * @ApiResponses: 응답 정보
 */
@Tag(name = "Post", description = "게시글 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 작성
     *
     * @PostMapping: HTTP POST 요청 매핑
     * @RequestBody: HTTP 요청 본문을 자바 객체로 변환 (역직렬화)
     * @Valid: 유효성 검증 활성화 (DTO의 Bean Validation 적용)
     *
     * ResponseEntity: HTTP 응답을 세밀하게 제어
     * - 상태 코드, 헤더, 본문 설정 가능
     */
    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostRequestDto requestDto) {

        PostResponseDto response = postService.createPost(requestDto);

        // 201 Created 상태 코드 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 게시글 목록 조회 (페이징)
     *
     * @GetMapping: HTTP GET 요청 매핑
     * @PageableDefault: Pageable 기본값 설정
     * - page: 페이지 번호 (0부터 시작)
     * - size: 페이지 크기
     * - sort: 정렬 기준
     *
     * 요청 예시: GET /api/posts?page=0&size=10&sort=createdAt,desc
     */
    @Operation(summary = "게시글 목록 조회", description = "페이징된 게시글 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<Page<PostListResponseDto>> getAllPosts(
            @Parameter(description = "페이지 정보")
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<PostListResponseDto> response = postService.getAllPosts(pageable);

        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 단건 조회
     *
     * @PathVariable: URL 경로의 변수를 매핑
     * 예: /api/posts/1 -> id = 1
     */
    @Operation(summary = "게시글 조회", description = "게시글 상세 정보를 조회합니다 (조회수 증가)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id) {

        PostResponseDto response = postService.getPost(id);

        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 수정
     *
     * @PutMapping: HTTP PUT 요청 매핑
     * PUT vs PATCH:
     * - PUT: 리소스 전체 교체
     * - PATCH: 리소스 부분 수정
     */
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto requestDto) {

        PostResponseDto response = postService.updatePost(id, requestDto);

        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 삭제
     *
     * @DeleteMapping: HTTP DELETE 요청 매핑
     * 204 No Content: 삭제 성공 시 응답 본문 없음
     */
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다 (댓글도 함께 삭제)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id) {

        postService.deletePost(id);

        // 204 No Content
        return ResponseEntity.noContent().build();
    }

    /**
     * 게시글 검색
     *
     * @RequestParam: 쿼리 파라미터 매핑
     * 예: /api/posts/search?keyword=스프링
     */
    @Operation(summary = "게시글 검색", description = "제목/내용으로 게시글을 검색합니다")
    @ApiResponse(responseCode = "200", description = "검색 성공")
    @GetMapping("/search")
    public ResponseEntity<Page<PostListResponseDto>> searchPosts(
            @Parameter(description = "검색 키워드", example = "스프링")
            @RequestParam String keyword,
            @Parameter(description = "페이지 정보")
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<PostListResponseDto> response = postService.searchPosts(keyword, pageable);

        return ResponseEntity.ok(response);
    }
}
