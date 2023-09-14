package kr.co.fastcampus.board.dto;

import kr.co.fastcampus.board.domain.Article;
import kr.co.fastcampus.board.domain.ArticleComment;

import java.time.LocalDateTime;

public record ArticleCommentDTO(
        Long id,
        Long articleId,
        UserAccountDTO userAccountDTO,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy){
    public static ArticleCommentDTO of(Long id,
                                       Long articleId,
                                       UserAccountDTO userAccountDTO,
                                       String content,
                                       LocalDateTime createdAt,
                                       String createdBy,
                                       LocalDateTime modifiedAt,
                                       String modifiedBy) {
        return new ArticleCommentDTO(id, articleId, userAccountDTO, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }
    public static ArticleCommentDTO from(ArticleComment entity){
        return new ArticleCommentDTO(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDTO.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
    public ArticleComment toEntity(Article entity){
        return ArticleComment.of(
                entity,
                userAccountDTO.toEntity(),
                content
        );
    }
}
