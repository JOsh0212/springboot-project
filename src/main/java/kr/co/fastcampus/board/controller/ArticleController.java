package kr.co.fastcampus.board.controller;

import kr.co.fastcampus.board.domain.type.SearchType;
import kr.co.fastcampus.board.dto.response.ArticleResponse;
import kr.co.fastcampus.board.dto.response.ArticleWithCommentResponse;
import kr.co.fastcampus.board.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap modelMap){
        modelMap.addAttribute("articles", articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from));
        return "articles/index";
    }
    @GetMapping("/{articleId}")
    public String articles(@PathVariable Long articleId, ModelMap modelMap){
        ArticleWithCommentResponse article = ArticleWithCommentResponse.from(articleService.getArticle(articleId));
        modelMap.addAttribute("article", article);
        modelMap.addAttribute("articleComments", article.articleCommentResponses());
        return "articles/detail";
    }
}
