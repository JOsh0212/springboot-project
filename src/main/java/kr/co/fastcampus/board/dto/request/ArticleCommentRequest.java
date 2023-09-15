package kr.co.fastcampus.board.dto.request;

import kr.co.fastcampus.board.dto.ArticleCommentDTO;
import kr.co.fastcampus.board.dto.UserAccountDTO;

public record ArticleCommentRequest (
              Long articleId,
              String content
){
    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }
    public ArticleCommentDTO toDTO(UserAccountDTO userAccountDTO){
        return ArticleCommentDTO.of(
                articleId,
                userAccountDTO,
                content
        );
    }
}
