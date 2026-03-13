package com.example.board.dto.request;

import com.example.board.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 요청 DTO (Data Transfer Object)
 *
 * DTO를 사용하는 이유:
 * 1. 엔티티를 직접 노출하지 않음 (보안, 캡슐화)
 * 2. API 스펙과 엔티티 분리 (유연한 변경)
 * 3. 필요한 필드만 전달 (효율성)
 * 4. 유효성 검증 로직 분리
 *
 * Bean Validation 어노테이션:
 * @NotBlank: null, 빈 문자열, 공백만 있는 문자열 모두 불허
 * @NotNull: null만 불허 (빈 문자열은 허용)
 * @NotEmpty: null, 빈 문자열 불허 (공백 문자열은 허용)
 * @Size: 문자열 길이 제한
 */
@Getter
@NoArgsConstructor
@Schema(description = "게시글 요청 DTO")
public class PostRequestDto {

    @Schema(description = "게시글 제목", example = "첫 번째 게시글입니다")
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다")
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용을 작성합니다.")
    @NotBlank(message = "내용은 필수입니다")
    private String content;

    @Schema(description = "작성자", example = "홍길동")
    @NotBlank(message = "작성자는 필수입니다")
    @Size(max = 50, message = "작성자는 50자를 초과할 수 없습니다")
    private String author;

    @Builder
    public PostRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /**
     * DTO -> Entity 변환 메서드
     * 서비스 계층에서 사용
     */
    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .build();
    }
}
