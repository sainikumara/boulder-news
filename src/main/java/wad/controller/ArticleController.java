package wad.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Article;
import wad.domain.Category;
import wad.domain.PageView;
import wad.domain.Picture;
import wad.domain.Writer;
import wad.repository.ArticleRepository;
import wad.repository.CategoryRepository;
import wad.repository.PageViewRepository;
import wad.repository.PictureRepository;
import wad.repository.WriterRepository;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
            
    @Autowired
    private WriterRepository writerRepository;
    
    @Autowired
    private PageViewRepository pageViewRepository;

    @GetMapping("/")
    public String frontpage(Model model,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="5") int limit) {
        model.addAttribute("articles", this.articleRepository.findAll(this.newestOnList(page, limit)));
        model.addAttribute("categories", this.categoryRepository.findAll());
        
        return "index";
    }
    
    public Pageable newestOnList(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.DESC, "publishingTime");
        
        return pageable;
    }
    
    @GetMapping("/add_article")
    public String addArticle(Model model) {
        model.addAttribute("categories", this.categoryRepository.findAll());
        model.addAttribute("writers", this.writerRepository.findAll());
        return "add_article";
    }

    @GetMapping("/articles/{id}")
    public String viewArticle(Model model, @PathVariable Long id) {
        // TODO direct to error page if article doesn't exist
        if (this.articleRepository.existsById(id)) {
            model.addAttribute("categories", this.categoryRepository.findAll());
            
            Article article = this.articleRepository.getOne(id);
            this.addPageView(article);

            model.addAttribute("article", article);
            model.addAttribute("picture", article.getPicture());
            model.addAttribute("writers", article.getWriters());
            
            int listSize = 5;
            model.addAttribute("mostread", this.getMostReadLastWeek(listSize));
            model.addAttribute("newest", this.articleRepository.findAll(this.newestOnList(0, listSize)));

            return "article";
        }
        return "index";
    }
    
    private void addPageView(Article article) {
        PageView pageView = new PageView();
        pageView.setViewTime(LocalDate.now());
        pageView.setArticle(article);

        article.getPageViews().add(pageView);
        
        this.pageViewRepository.save(pageView);
        this.articleRepository.save(article);
    }
    
    private List<Article> getMostReadLastWeek(int preferredListSize) {
        List<Article> readLastWeek = 
            this.articleRepository.mostReadSince(LocalDate.now().minusWeeks(1L));
        int realListSize;
        
        if (readLastWeek.size() < preferredListSize) {
            realListSize = readLastWeek.size();
        } else {
            realListSize = preferredListSize;
        }
        
        List<Article> mostReadLastWeek = readLastWeek.subList(0, realListSize);
        
        return mostReadLastWeek;
    }
    
    @PostMapping("/add_article")
    public String create(@RequestParam String title,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishingTime,
            @RequestParam String content,
            @RequestParam String lead,
            @RequestParam List<Category> categories,
            @RequestParam List<Writer> writers,
            @RequestParam("file") MultipartFile file) throws IOException {
        Article article = new Article();
        article.setTitle(title);
        article.setLead(lead);
        article.setPublishingTime(publishingTime);
        article.setContent(content);
        article.setCategories(categories);
        article.setWriters(writers);

        if(file.getContentType().equals("image/jpeg")) {
            // TODO allow larger files, reduce size?
            Picture picture = new Picture();
            picture.setContent(file.getBytes());
            this.pictureRepository.save(picture);
            article.setPicture(picture);
        }
        
        this.articleRepository.save(article);
        return "redirect:/";
    }
    
    @Transactional
    @DeleteMapping("/articles/{id}")
    public String removeArticle(@PathVariable Long id) {
        Article article = this.articleRepository.getOne(id);
        this.pictureRepository.delete(article.getPicture());
        
        article.getCategories().forEach((category) -> {
            this.categoryRepository.getOne(category.getId()).getArticles().remove(article);
        });
        
        article.getWriters().forEach((writer) -> {
            this.writerRepository.getOne(writer.getId()).getArticles().remove(article);
        });
        
        this.articleRepository.delete(article);
        
        return "redirect:/";
    }
    
}
