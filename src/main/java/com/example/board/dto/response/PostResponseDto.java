package com.example.board.dto.response;

import com.example.board.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 상세 응답 DTO (댓글 포함)
 */
@Getter
@Schema(description = "게시글 상세 응답 DTO")
public class PostResponseDto {

    @Schema(description = "게시글 ID", example = "1")
    private final Long id;

    @Schema(description = "제목", example = "첫 번째 게시글입니다")
    private final String title;

    @Schema(description = "내용", example = "게시글 내용입니다.")
    private final String content;

    @Schema(description = "작성자", example = "홍길동")
    private final String author;

    @Schema(description = "조회수", example = "100")
    private final Integer viewCount;

    @Schema(description = "댓글 목록")
    private final List<CommentResponseDto> comments;

    @Schema(description = "댓글 개수", example = "5")
    private final Integer commentCount;

    @Schema(description = "생성일시")
    private final LocalDateTime createdAt;

    @Schema(description = "수정일시")
    private final LocalDateTime updatedAt;

    @Builder
    private PostResponseDto(Long id, String title, String content, String author,
                            Integer viewCount, List<CommentResponseDto> comments,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = viewCount;
        this.comments = comments;
        this.commentCount = comments != null ? comments.size() : 0;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Entity -> DTO 변환 (댓글 포함)
     */
    public static PostResponseDto from(Post post) {
        List<CommentResponseDto> commentDtos = post.getComments().stream()
                .map(CommentResponseDto::from)
                .toList();

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .viewCount(post.getViewCount())
                .comments(commentDtos)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
