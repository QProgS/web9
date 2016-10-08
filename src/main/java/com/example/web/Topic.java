package com.example.web;

import com.example.service.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Topic {

    private ArticleRepository articleRepository;

    @Autowired
    public Topic(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

}
