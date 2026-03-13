package com.example.board.exception;

/**
 * 댓글을 찾을 수 없을 때 발생하는 예외
 */
public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(Long id) {
        super("댓글을 찾을 수 없습니다. ID: " + id);
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
