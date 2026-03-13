package com.example.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 요청 DTO
 */
@Getter
@NoArgsConstructor
@Schema(description = "댓글 요청 DTO")
public class CommentRequestDto {

    @Schema(description = "댓글 내용", example = "좋은 글이네요!")
    @NotBlank(message = "댓글 내용은 필수입니다")
    @Size(max = 500, message = "댓글은 500자를 초과할 수 없습니다")
    private String content;

    @Schema(description = "작성자", example = "댓글러")
    @NotBlank(message = "작성자는 필수입니다")
    @Size(max = 50, message = "작성자는 50자를 초과할 수 없습니다")
    private String author;

    @Builder
    public CommentRequestDto(String content, String author) {
        this.content = content;
        this.author = author;
    }
}
