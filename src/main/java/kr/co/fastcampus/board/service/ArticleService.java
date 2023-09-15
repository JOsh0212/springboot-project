package kr.co.fastcampus.board.service;

import jakarta.persistence.EntityNotFoundException;
import kr.co.fastcampus.board.domain.Article;
import kr.co.fastcampus.board.domain.type.SearchType;
import kr.co.fastcampus.board.dto.ArticleDTO;
import kr.co.fastcampus.board.dto.ArticleWithCommentsDTO;
import kr.co.fastcampus.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDTO> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword==null||searchKeyword.isBlank()){
            return articleRepository.findAll(pageable).map(ArticleDTO::from);
        }
        return switch (searchType){
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword,pageable).map(ArticleDTO::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword,pageable).map(ArticleDTO::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword,pageable).map(ArticleDTO::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword,pageable).map(ArticleDTO::from);
            case HASHTAG -> articleRepository.findByHashtag("#"+searchKeyword,pageable).map(ArticleDTO::from);
        };
    }
    public ArticleDTO getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDTO::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }
    @Transactional(readOnly = true)
    public ArticleWithCommentsDTO getArticleWithCommnets(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDTO::from)
                .orElseThrow(()-> new EntityNotFoundException("게시글이 없습니다. - articleId: "+articleId));
    }

    public void saveArticle(ArticleDTO dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(Long articleId,ArticleDTO dto) {
//        Article article = articleRepository.findById(dto.id()); // 있는지 확인하고 -> 있으면 저장
//        article.setHashtag("asdfsdfds");
//        articleRepository.save(article);
//        Article article = articleRepository.getOne(); // 없어짐 -> ReferenceById로 바뀜
        try{//게시물이 없을 경우를 처리하기 위해
            Article article = articleRepository.getReferenceById(articleId); //getReferenceById에서 EntityNotFoundException을 던짐
            if(dto.title()!=null){article.setTitle(dto.title());}    //record는 알아서 getter, setter를 만들어줌
            if(dto.content()!=null){article.setContent(dto.content());}
            article.setHashtag(dto.hashtag());
            //따로 save 안해도 됨, 하고 싶으면 save하고 flush해도 됨
        }catch (EntityNotFoundException e){
            log.warn("게시글 업데이트 실패, 게시글을 찾을 수 없습니다 - dto: {}",dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }
    @Transactional(readOnly = true)
    public Page<ArticleDTO> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if(hashtag ==null || hashtag.isBlank()){
            return Page.empty(pageable);
        }
        return articleRepository.findByHashtag(hashtag,pageable).map(ArticleDTO::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }


}
