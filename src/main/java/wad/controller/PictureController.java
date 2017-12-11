package wad.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Picture;
import wad.repository.ArticleRepository;
import wad.repository.PictureRepository;


@Controller
@Transactional
public class PictureController {
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @GetMapping("/pictures")
    public String redirect(Model model) {
        model.addAttribute("count", this.pictureRepository.findAll().size());
        model.addAttribute("pictures", this.pictureRepository.findAll());
        return "pictures";
    }
    
    @GetMapping("/pictures/{id}")
    public String showGif(Model model, @PathVariable Long id) {
        
        if (this.pictureRepository.existsById(id)) {
            model.addAttribute("current", id);
        }
        
        return "pictures";
    }
    
    @GetMapping(path = "/pictures/{id}/content", produces = "image/jpeg")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return this.pictureRepository.getOne(id).getContent();
    }
    
    @GetMapping(path = "/pictures_by_article/{id}/content", produces = "image/jpeg")
    @ResponseBody
    public byte[] getByArticleId(@PathVariable Long id) {
        return this.articleRepository.getOne(id).getPicture().getContent();
    }
    
    @PostMapping("/pictures")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {
        
        if(file.getContentType().equals("image/jpeg")) {
            Picture picture = new Picture();
            picture.setContent(file.getBytes());
            this.pictureRepository.save(picture);
        }
        
        return "redirect:/pictures";
    }
}
