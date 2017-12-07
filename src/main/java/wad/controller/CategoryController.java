package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Category;
import wad.repository.CategoryRepository;

@Controller
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String listCategories(Model model) {

        model.addAttribute("categories", this.categoryRepository.findAll());
        
        return "categories";
    }

    @GetMapping("/categories/{id}")
    public String viewCategory(Model model, @PathVariable Long id) {
        Category category = this.categoryRepository.getOne(id);
        model.addAttribute("category", category);
        model.addAttribute("articles", category.getArticles());
        
        return "category";
    }

    @PostMapping("/categories")
    public String addCategory(@RequestParam String name) {
        Category category = new Category();
        category.setName(name);
        this.categoryRepository.save(category);

        return "redirect:/categories";
    }
}
