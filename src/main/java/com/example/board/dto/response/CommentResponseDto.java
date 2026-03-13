package com.example.board.dto.response;

import com.example.board.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 댓글 응답 DTO
 *
 * Entity -> DTO 변환을 위한 정적 팩토리 메서드 패턴 사용
 */
@Getter
@Schema(description = "댓글 응답 DTO")
public class CommentResponseDto {

    @Schema(description = "댓글 ID", example = "1")
    private final Long id;

    @Schema(description = "댓글 내용", example = "좋은 글이네요!")
    private final String content;

    @Schema(description = "작성자", example = "댓글러")
    private final String author;

    @Schema(description = "생성일시")
    private final LocalDateTime createdAt;

    @Schema(description = "수정일시")
    private final LocalDateTime updatedAt;

    @Builder
    private CommentResponseDto(Long id, String content, String author,
                               LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * 정적 팩토리 메서드: Entity -> DTO 변환
     *
     * 장점:
     * 1. 이름을 가질 수 있음 (가독성)
     * 2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 됨
     * 3. 반환 타입의 하위 타입 객체를 반환할 수 있음
     */
    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
