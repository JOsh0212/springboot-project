package kr.co.fastcampus.board.controller;

import kr.co.fastcampus.board.domain.type.FormStatus;
import kr.co.fastcampus.board.domain.type.SearchType;
import kr.co.fastcampus.board.dto.request.ArticleRequest;
import kr.co.fastcampus.board.dto.response.ArticleResponse;
import kr.co.fastcampus.board.dto.response.ArticleWithCommentResponse;
import kr.co.fastcampus.board.dto.security.BoardPrinciple;
import kr.co.fastcampus.board.service.ArticleService;
import kr.co.fastcampus.board.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;
    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap modelMap){
        Page<ArticleResponse> articles = articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),articles.getTotalPages());

        modelMap.addAttribute("articles", articles);
        modelMap.addAttribute("paginationBarNumbers", barNumbers);
        modelMap.addAttribute("searchTypes", SearchType.values());
        return "articles/index";
    }
    @GetMapping("/{articleId}")
    public String articles(@PathVariable Long articleId, ModelMap modelMap){
        ArticleWithCommentResponse article = ArticleWithCommentResponse.from(articleService.getArticleWithCommnets(articleId));
        modelMap.addAttribute("article", article);
        modelMap.addAttribute("articleComments", article.articleCommentResponses());
        modelMap.addAttribute("totalCount", articleService.getArticleCount());
        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchArticleHashtag( @RequestParam(required = false) String searchValue,
                                 @PageableDefault(size=10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                 ModelMap modelMap){
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue,pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),articles.getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        modelMap.addAttribute("articles", articles);
        modelMap.addAttribute("hashtags", hashtags);
        modelMap.addAttribute("paginationBarNumbers", barNumbers);
        modelMap.addAttribute("searchType", SearchType.HASHTAG);
        return "articles/search-hashtag";
    }
    @GetMapping("/form")
    public String articleForm(ModelMap map){    //글생성 페이지
        map.addAttribute("formStatus", FormStatus.CREATE);
        return "articles/form";
    }

    @PostMapping("/form")
    public String postNewArticle(ArticleRequest articleRequest,@AuthenticationPrincipal BoardPrinciple boardPrinciple){//글생성 요청
        articleService.saveArticle(articleRequest.toDTO(boardPrinciple.toDTO()));
        return "redirect:/articles";
    }

    @GetMapping("/{articleId}/form")
    public String updateArticleForm(@PathVariable Long articleId,ModelMap modelMap){    // 글 수정 페이지
        ArticleResponse article=ArticleResponse.from(articleService.getArticle(articleId));
        modelMap.addAttribute("article",article);
        modelMap.addAttribute("formStatus",FormStatus.UPDATE);
        return "articles/form";
    }
    @PostMapping("/{articleId}/form")
    public String updateArticle(@PathVariable Long articleId,
                                ArticleRequest articleRequest,
                                @AuthenticationPrincipal BoardPrinciple boardPrinciple) {   // 글 수정 요청
        articleService.updateArticle(articleId,articleRequest.toDTO(boardPrinciple.toDTO()));
        return "redirect:/articles/" + articleId;
    }
    @PostMapping("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId,
                                @AuthenticationPrincipal BoardPrinciple boardPrinciple){
        articleService.deleteArticle(articleId,boardPrinciple.getUsername());

        return "redirect:/articles";
    }
}
