package com.example.board.dto.response;

import com.example.board.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 게시글 목록 응답 DTO (간략 정보)
 *
 * 목록 조회 시에는 내용, 댓글 등 상세 정보 제외
 * 필요한 정보만 전달하여 응답 크기 최소화
 */
@Getter
@Schema(description = "게시글 목록 응답 DTO")
public class PostListResponseDto {

    @Schema(description = "게시글 ID", example = "1")
    private final Long id;

    @Schema(description = "제목", example = "첫 번째 게시글입니다")
    private final String title;

    @Schema(description = "작성자", example = "홍길동")
    private final String author;

    @Schema(description = "조회수", example = "100")
    private final Integer viewCount;

    @Schema(description = "댓글 개수", example = "5")
    private final Integer commentCount;

    @Schema(description = "생성일시")
    private final LocalDateTime createdAt;

    @Builder
    private PostListResponseDto(Long id, String title, String author,
                                Integer viewCount, Integer commentCount,
                                LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
    }

    /**
     * Entity -> DTO 변환 (목록용)
     */
    public static PostListResponseDto from(Post post) {
        return PostListResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .viewCount(post.getViewCount())
                .commentCount(post.getComments().size())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
