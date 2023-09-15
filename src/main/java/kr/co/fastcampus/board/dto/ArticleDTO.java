package kr.co.fastcampus.board.dto;

import kr.co.fastcampus.board.domain.Article;

import java.time.LocalDateTime;

public record ArticleDTO(Long id,
                         UserAccountDTO userAccountDTO,
                         String title,
                         String content,
                         String hashtag,
                         LocalDateTime createdAt,
                         String createdBy,
                         LocalDateTime modifiedAt,
                         String modifiedBy){
    public static ArticleDTO of(Long id,
                                UserAccountDTO userAccountDTO,
                                String title,
                                String content,
                                String hashtag,
                                LocalDateTime createdAt,
                                String createdBy,
                                LocalDateTime modifiedAt,
                                String modifiedBy) {
        return new ArticleDTO(id, userAccountDTO, title, content, hashtag, createdAt, createdBy,modifiedAt,modifiedBy);
    }
    public static ArticleDTO from(Article entity) {
        return new ArticleDTO(
                entity.getId(),
                UserAccountDTO.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public static ArticleDTO of(UserAccountDTO userAccountDTO, String title, String content, String hashtag) {
        return new ArticleDTO(null, userAccountDTO, title, content, hashtag, null, null,null,null);
    }

    public Article toEntity() {
        return Article.of(
                userAccountDTO.toEntity(),
                title,
                content,
                hashtag
        );
    }
}
