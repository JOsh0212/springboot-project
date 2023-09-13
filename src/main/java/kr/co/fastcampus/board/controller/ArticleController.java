package kr.co.fastcampus.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @GetMapping
    public String articles(ModelMap modelMap){
        modelMap.addAttribute("articles", List.of());
        return "articles/index";
    }
}
