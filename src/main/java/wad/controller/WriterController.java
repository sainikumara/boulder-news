package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Writer;
import wad.repository.WriterRepository;

@Controller
public class WriterController {
    
    @Autowired
    private WriterRepository writerRepository;

    @GetMapping("/writers")
    public String listCategories(Model model) {

        model.addAttribute("writers", this.writerRepository.findAll());
        
        return "writers";
    }

    @GetMapping("/writers/{id}")
    public String viewWriter(Model model, @PathVariable Long id) {
        Writer writer = this.writerRepository.getOne(id);
        model.addAttribute("writer", writer);
        model.addAttribute("articles", writer.getArticles());
        
        return "writer";
    }

    @PostMapping("/writers")
    public String addWriter(@RequestParam String name) {
        Writer writer = new Writer();
        writer.setName(name);
        this.writerRepository.save(writer);

        return "redirect:/writers";
    }
}
