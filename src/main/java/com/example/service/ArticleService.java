package com.example.service;

import com.example.domain.Article;
import com.example.domain.Word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Component
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private ArticleRepository articleRepository;
    private WordRepository wordRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, WordRepository wordRepository){
        this.articleRepository = articleRepository;
        this.wordRepository = wordRepository;
    }

    public Article save(Article article){
        article.words = new ArrayList<>();
        Set<String> list = new HashSet<>(Arrays.asList(article.content.split("\\W+")));

        logger.info(list + " split list.");
        for (String aList : list) {
            Word word = wordRepository.findByName(aList);
            if (word != null){
                article.words.add(word);
            }
        }
        logger.info(article.words.size() + " words were found.");
        return articleRepository.save(article);
    }

}
