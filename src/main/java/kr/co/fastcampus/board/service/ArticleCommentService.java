package kr.co.fastcampus.board.service;

import kr.co.fastcampus.board.dto.ArticleCommentDTO;
import kr.co.fastcampus.board.repository.ArticleCommentRepository;
import kr.co.fastcampus.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    @Transactional(readOnly = true)
    public List<ArticleCommentDTO> searchArticleComments(Long articleId) {
        return List.of();
    }
    public void saveArticleComment(ArticleCommentDTO dto) {
    }

    public void updateArticleComment(ArticleCommentDTO dto) {
    }

    public void deleteArticleComment(Long articleCommentId) {
    }
}
