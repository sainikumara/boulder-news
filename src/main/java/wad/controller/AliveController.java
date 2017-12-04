package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Article;
import wad.repository.ArticleRepository;

@Controller
public class AliveController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }

    @PostMapping("/")
    public String create(@RequestParam String name) {
        Article article = new Article(name);
        articleRepository.save(article);
        return "redirect:/";
    }
}
