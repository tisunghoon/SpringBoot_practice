package com.example.board.controller;

import com.example.board.dto.request.CommentRequestDto;
import com.example.board.dto.response.CommentResponseDto;
import com.example.board.exception.ErrorResponse;
import com.example.board.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글 REST API 컨트롤러
 *
 * REST API URI 설계:
 * - 댓글은 게시글에 종속된 리소스
 * - 게시글 하위에 댓글 경로 구성: /api/posts/{postId}/comments
 * - 단독 댓글 조작은: /api/comments/{id}
 */
@Tag(name = "Comment", description = "댓글 API")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     *
     * URI: POST /api/posts/{postId}/comments
     * 게시글에 새 댓글 추가
     */
    @Operation(summary = "댓글 작성", description = "게시글에 새 댓글을 작성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto response = commentService.createComment(postId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 특정 게시글의 댓글 목록 조회
     *
     * URI: GET /api/posts/{postId}/comments
     */
    @Operation(summary = "댓글 목록 조회", description = "게시글의 댓글 목록을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId) {

        List<CommentResponseDto> response = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 수정
     *
     * URI: PUT /api/comments/{id}
     * 댓글은 게시글과 독립적으로 수정 가능
     */
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto response = commentService.updateComment(id, requestDto);

        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 삭제
     *
     * URI: DELETE /api/comments/{id}
     */
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "댓글 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long id) {

        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }
}
