package com.example.web;

import com.example.domain.Article;
import com.example.service.ArticleRepository;
import com.example.service.ArticleService;
import com.example.utils.PageNav;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/articles")
@SessionAttributes({"article"})
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
    public ModelAndView showAll(Pageable pageable) {

        PageNav<Article> articlesPageNav = new PageNav<>(articleRepository.findAll(pageable), pageable, 8);

        return new ModelAndView("articles")
                .addObject("articles", articlesPageNav)
                .addObject("pagePath", pagePath);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable Long id) {
        Article article = articleRepository.findOne(id);
        if(article == null) return new ModelAndView("error");

        return new ModelAndView("article")
                .addObject("article", article)
                .addObject("pagePath", pagePath);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getAdd() {
        logger.debug("Received request to show add article");

        return new ModelAndView("articleAdd")
                .addObject("article", new Article());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@Valid Article article, BindingResult result){
        logger.info("add article: " + article.getTitle() + " "  + article.getContent());

        ModelAndView modelAndView = new ModelAndView("articleAdd");
        if(result.hasErrors())
            modelAndView.addObject("article",article);
        else
            articleService.save(article);

        return modelAndView;
    }

}
