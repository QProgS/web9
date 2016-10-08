package com.example.service;

import com.example.domain.Article;
import com.example.domain.Word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class ArticleService {

    private ArticleRepository articleRepository;
    private WordRepository wordRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, WordRepository wordRepository){
        this.articleRepository = articleRepository;
        this.wordRepository = wordRepository;
    }

    public Article save(Article article){
        article.words = new ArrayList<>();
        String[] list = article.text.split(" ");
        for (String aList : list) {
            Word word = wordRepository.findByName(aList);
            if (word != null){
                article.words.add(word);
            }
        }
        return articleRepository.save(article);
    }

}
