package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wad.repository.ArticleRepository;
import wad.repository.CategoryRepository;
import wad.repository.PictureRepository;
import wad.repository.WriterRepository;

@Controller
public class DefaultController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private WriterRepository writerRepository;

    @GetMapping("/admin_panel")
    public String admin(Model model) {
        model.addAttribute("writers", this.writerRepository.findAll());
        model.addAttribute("categories", this.categoryRepository.findAll());
        model.addAttribute("articles", this.articleRepository.findAll());
        return "admin_panel";
    }
    
    @GetMapping("/admin_panel_writers")
    public String adminWriters(Model model) {
        model.addAttribute("writers", this.writerRepository.findAll());
        model.addAttribute("categories", this.categoryRepository.findAll());
        return "admin_panel_writers";
    }
    
    @GetMapping("/admin_panel_categories")
    public String adminCategories(Model model) {
        model.addAttribute("categories", this.categoryRepository.findAll());
        return "admin_panel_categories";
    }
}
