package kr.co.fastcampus.board.controller;

import kr.co.fastcampus.board.dto.request.ArticleCommentRequest;
import kr.co.fastcampus.board.dto.security.BoardPrinciple;
import kr.co.fastcampus.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //TODO: RestController로 바꾸기 , 수정도 구현
@RequestMapping("/comments")
@RequiredArgsConstructor
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest,
                                        @AuthenticationPrincipal BoardPrinciple boardPrinciple){
        articleCommentService.saveArticleComment(articleCommentRequest.toDTO(boardPrinciple.toDTO()));
        return "redirect:/articles/"+articleCommentRequest.articleId();
    }
    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId,
                                       @AuthenticationPrincipal BoardPrinciple boardPrinciple){

        articleCommentService.deleteArticleComment(commentId,boardPrinciple.getUsername());
        return "redirect:/articles/"+articleId;
    }
}
