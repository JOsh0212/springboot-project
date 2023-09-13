package kr.co.fastcampus.board.repository;

import kr.co.fastcampus.board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
