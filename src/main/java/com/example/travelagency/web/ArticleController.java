package com.example.travelagency.web;

import com.example.travelagency.services.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String articles(Model model){

        model.addAttribute("articles", articleService.getArticles());

        return "/articles";
    }

    @GetMapping("/articles/{id}")
    public String details(@PathVariable Long id, Model model){

        model.addAttribute("article", articleService.findById(id));

        return "article-details";
    }
}
