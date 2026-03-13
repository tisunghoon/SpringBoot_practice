package com.example.board.service;

import com.example.board.dto.request.CommentRequestDto;
import com.example.board.dto.response.CommentResponseDto;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.exception.CommentNotFoundException;
import com.example.board.exception.PostNotFoundException;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 댓글 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 작성
     *
     * 게시글 존재 여부 확인 후 댓글 생성
     */
    @Override
    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto) {
        log.info("Creating comment for post id: {}", postId);

        // 게시글 조회 (없으면 예외)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        // 댓글 생성
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .author(requestDto.getAuthor())
                .post(post)
                .build();

        // 저장
        Comment savedComment = commentRepository.save(comment);

        log.info("Comment created with id: {}", savedComment.getId());

        return CommentResponseDto.from(savedComment);
    }

    /**
     * 특정 게시글의 댓글 목록 조회
     */
    @Override
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        log.info("Getting comments for post id: {}", postId);

        // 게시글 존재 여부 확인
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }

        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentResponseDto::from)
                .toList();
    }

    /**
     * 댓글 수정
     */
    @Override
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        log.info("Updating comment with id: {}", id);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        // Dirty Checking으로 자동 업데이트
        comment.update(requestDto.getContent());

        log.info("Comment updated with id: {}", id);

        return CommentResponseDto.from(comment);
    }

    /**
     * 댓글 삭제
     */
    @Override
    @Transactional
    public void deleteComment(Long id) {
        log.info("Deleting comment with id: {}", id);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        commentRepository.delete(comment);

        log.info("Comment deleted with id: {}", id);
    }
}
