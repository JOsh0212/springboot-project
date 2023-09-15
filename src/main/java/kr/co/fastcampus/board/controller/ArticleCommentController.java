package kr.co.fastcampus.board.controller;

import kr.co.fastcampus.board.dto.UserAccountDTO;
import kr.co.fastcampus.board.dto.request.ArticleCommentRequest;
import kr.co.fastcampus.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
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
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest){
        //TODO: 인증정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDTO(UserAccountDTO.of(
                "uno" ,"asdf1234","uno@mail.com","UNO",null
        )));
        return "redirect:/articles/"+articleCommentRequest.articleId();
    }
    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId,Long articleId){
        //TODO: 인증정보를 넣어줘야 한다.
        articleCommentService.deleteArticleComment(commentId);
        return "redirect:/articles/"+articleId;
    }
}
