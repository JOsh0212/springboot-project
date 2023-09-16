package kr.co.fastcampus.board.dto.response;

import kr.co.fastcampus.board.dto.ArticleCommentDTO;

import java.time.LocalDateTime;

public record ArticleCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String userId,
        String nickname
) {
    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String userId,String nickname) {
        return new ArticleCommentResponse(id, content, createdAt, email, userId,nickname);
    }
    public static ArticleCommentResponse from(ArticleCommentDTO dto) {
        String nickname = dto.userAccountDTO().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDTO().userId();
        }

        return new ArticleCommentResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDTO().email(),
                dto.userAccountDTO().userId(),
                nickname
        );
    }
}
