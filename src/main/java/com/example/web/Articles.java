package com.example.web;

import com.example.domain.Article;
import com.example.service.ArticleRepository;
import com.example.service.ArticleService;
import com.example.utils.PageNav;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/articles")
public class Articles {
    private static final Logger logger = LoggerFactory.getLogger(Articles.class);
    private static final String pagePath = "articles";

    private ArticleRepository articleRepository;
    private ArticleService articleService;

    @Autowired
    public Articles(ArticleRepository articleRepository, ArticleService articleService){
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAll( @PageableDefault(value = 5)Pageable pageable) {
        Pageable pageable1 = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),
                Sort.Direction.DESC, "created");
        PageNav<Article> articlesPageNav = new PageNav<>(articleRepository.findAll(pageable1), pageable1, 7);

        for(Article article : articlesPageNav.getContent()){
            if(article.content.length() > 350){
                article.content = article.content.substring(0,350) + "...";
            }
        }

        return new ModelAndView("articles")
                .addObject("articles", articlesPageNav)
                .addObject("pagePath", pagePath);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable Long id) {
        Article article = articleService.findOne(id);
        if(article == null) return new ModelAndView("error");

        return new ModelAndView("article")
                .addObject("article", article)
                .addObject("pagePath", pagePath);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        logger.debug("Received request to show add article");

        return new ModelAndView("articleAdd")
                .addObject("article", new Article());
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id) {
        logger.debug("Received request to edit article");

        return new ModelAndView("articleAdd")
                .addObject("article", articleRepository.findOne(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid Article article, BindingResult result){
        logger.info("add article: " + article.getTitle() + " "  + article.getContent());

        ModelAndView modelAndView = new ModelAndView("/add");
        if(result.hasErrors()) {
            modelAndView.addObject("article", article);
        } else {
            articleService.save(article);
            modelAndView.setViewName("redirect:/" + pagePath + "/" + article.getId());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable Long id) {
        articleRepository.delete(id);

        return "redirect:/" + pagePath;
    }

}
