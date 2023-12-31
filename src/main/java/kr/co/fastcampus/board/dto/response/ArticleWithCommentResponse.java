package kr.co.fastcampus.board.dto.response;

import kr.co.fastcampus.board.dto.ArticleWithCommentsDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentResponse(Long id,
                                         String title,
                                         String content,
                                         String hashtag,
                                         LocalDateTime createdAt,
                                         String email,
                                         String userId,
                                         String nickname,
                                         Set<ArticleCommentResponse> articleCommentResponses) implements Serializable {

    public static ArticleWithCommentResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String userId, String nickname, Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentResponse(id, title, content, hashtag, createdAt, email, userId,nickname, articleCommentResponses);
    }
    public static ArticleWithCommentResponse from(ArticleWithCommentsDTO dto) {
        String nickname = dto.userAccountDTO().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDTO().userId();
        }

        return new ArticleWithCommentResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDTO().email(),
                dto.userAccountDTO().userId(),
                nickname,
                dto.articleCommentDTOs().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
