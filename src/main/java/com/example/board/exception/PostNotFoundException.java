package com.example.board.exception;

/**
 * 게시글을 찾을 수 없을 때 발생하는 예외
 *
 * RuntimeException을 상속받는 이유:
 * - Unchecked Exception (컴파일 시 체크되지 않음)
 * - 호출하는 곳에서 try-catch나 throws 선언 불필요
 * - 비즈니스 로직에서 발생하는 예외는 보통 RuntimeException 사용
 */
public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super("게시글을 찾을 수 없습니다. ID: " + id);
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
