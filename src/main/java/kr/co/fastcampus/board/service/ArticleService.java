package kr.co.fastcampus.board.service;

import kr.co.fastcampus.board.domain.type.SearchType;
import kr.co.fastcampus.board.dto.ArticleDTO;
import kr.co.fastcampus.board.dto.ArticleWithCommentsDTO;
import kr.co.fastcampus.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDTO> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDTO getArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDTO dto) {
    }

    public void updateArticle(ArticleDTO dto) {
    }

    public void deleteArticle(long articleId) {
    }
}
