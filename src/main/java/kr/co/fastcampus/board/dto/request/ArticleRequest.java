package kr.co.fastcampus.board.dto.request;

import kr.co.fastcampus.board.dto.ArticleDTO;
import kr.co.fastcampus.board.dto.UserAccountDTO;

public record ArticleRequest(String title,
                             String content,
                             String hashtag) {
    public static ArticleRequest of(String title, String content, String hashtag) {
        return new ArticleRequest(title, content, hashtag);
    }
    public ArticleDTO toDTO(UserAccountDTO userAccountDTO){
        return ArticleDTO.of(
                userAccountDTO,
                title,
                content,
                hashtag
        );
    }
}
