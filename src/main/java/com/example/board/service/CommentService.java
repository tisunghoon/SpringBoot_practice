package com.example.board.service;

import com.example.board.dto.request.CommentRequestDto;
import com.example.board.dto.response.CommentResponseDto;

import java.util.List;

/**
 * 댓글 서비스 인터페이스
 */
public interface CommentService {

    /**
     * 댓글 작성
     *
     * @param postId 게시글 ID
     * @param requestDto 댓글 작성 요청 데이터
     * @return 생성된 댓글 정보
     */
    CommentResponseDto createComment(Long postId, CommentRequestDto requestDto);

    /**
     * 특정 게시글의 댓글 목록 조회
     *
     * @param postId 게시글 ID
     * @return 댓글 목록
     */
    List<CommentResponseDto> getCommentsByPostId(Long postId);

    /**
     * 댓글 수정
     *
     * @param id 댓글 ID
     * @param requestDto 수정할 데이터
     * @return 수정된 댓글 정보
     */
    CommentResponseDto updateComment(Long id, CommentRequestDto requestDto);

    /**
     * 댓글 삭제
     *
     * @param id 댓글 ID
     */
    void deleteComment(Long id);
}
