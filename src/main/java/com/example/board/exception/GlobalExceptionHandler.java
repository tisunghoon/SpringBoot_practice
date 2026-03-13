package com.example.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 전역 예외 처리기
 *
 * @RestControllerAdvice: @ControllerAdvice + @ResponseBody
 * - 모든 @RestController에서 발생하는 예외를 한 곳에서 처리
 * - 예외 처리 로직을 컨트롤러에서 분리하여 코드 가독성 향상
 *
 * @ExceptionHandler: 특정 예외 타입을 처리하는 메서드 지정
 *
 * @Slf4j: Lombok - Logger 객체 자동 생성 (log 변수)
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 게시글 없음 예외 처리
     *
     * @param e 발생한 예외
     * @return 404 Not Found 응답
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        log.error("PostNotFoundException: {}", e.getMessage());

        ErrorResponse response = ErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 댓글 없음 예외 처리
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e) {
        log.error("CommentNotFoundException: {}", e.getMessage());

        ErrorResponse response = ErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 유효성 검증 실패 예외 처리
     *
     * @Valid 검증 실패 시 MethodArgumentNotValidException 발생
     * 각 필드별 에러 메시지를 수집하여 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Validation failed: {}", e.getMessage());

        List<ErrorResponse.FieldError> fieldErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .rejectedValue(error.getRejectedValue())
                        .build())
                .toList();

        ErrorResponse response = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "입력값 검증에 실패했습니다",
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 그 외 모든 예외 처리 (최후의 방어선)
     *
     * 예상치 못한 예외 발생 시 500 Internal Server Error 반환
     * 실제 예외 메시지는 노출하지 않고 로그에만 기록 (보안)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unexpected error occurred", e);

        ErrorResponse response = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 내부 오류가 발생했습니다"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
