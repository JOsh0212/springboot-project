package kr.co.fastcampus.board.repository.querydsl;

import kr.co.fastcampus.board.domain.Article;
import kr.co.fastcampus.board.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom{
    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

//        JPQLQuery<String> query = from(article)
//                .distinct()
//                .select(article.hashtag)
//                .where(article.hashtag.isNotNull())
//                .fetch();
        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();
    }
}
