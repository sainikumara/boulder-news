package wad.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Article;
import wad.repository.ArticleRepository;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }

    @GetMapping("/articles/{id}")
    public String viewExam(Model model, @PathVariable Long id) {
        model.addAttribute("article", articleRepository.getOne(id));
        
        return "article";
    }
    
    @PostMapping("/")
    public String create(@RequestParam String title,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishingTime) {
        Article article = new Article();
        article.setTitle(title);
        article.setPublishingTime(publishingTime);
        articleRepository.save(article);
        return "redirect:/";
    }
}
