package com.example.board.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 에러 응답 DTO
 *
 * API에서 에러 발생 시 일관된 형식으로 응답하기 위한 클래스
 */
@Getter
@Schema(description = "에러 응답")
public class ErrorResponse {

    @Schema(description = "HTTP 상태 코드", example = "404")
    private final int status;

    @Schema(description = "에러 메시지", example = "게시글을 찾을 수 없습니다.")
    private final String message;

    @Schema(description = "에러 발생 시각")
    private final LocalDateTime timestamp;

    @Schema(description = "검증 에러 상세 (유효성 검증 실패 시)")
    private final List<FieldError> errors;

    @Builder
    private ErrorResponse(int status, String message, List<FieldError> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    /**
     * 단순 에러 응답 생성
     */
    public static ErrorResponse of(int status, String message) {
        return ErrorResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    /**
     * 유효성 검증 에러 응답 생성 (필드별 에러 포함)
     */
    public static ErrorResponse of(int status, String message, List<FieldError> errors) {
        return ErrorResponse.builder()
                .status(status)
                .message(message)
                .errors(errors)
                .build();
    }

    /**
     * 필드별 에러 정보
     */
    @Getter
    @Schema(description = "필드 에러 정보")
    public static class FieldError {

        @Schema(description = "에러 발생 필드", example = "title")
        private final String field;

        @Schema(description = "에러 메시지", example = "제목은 필수입니다")
        private final String message;

        @Schema(description = "거부된 값", example = "")
        private final Object rejectedValue;

        @Builder
        private FieldError(String field, String message, Object rejectedValue) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
        }
    }
}
