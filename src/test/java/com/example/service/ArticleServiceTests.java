package com.example.service;

import com.example.domain.Article;
import com.example.domain.Word;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ArticleServiceTests {

    private ArticleService articleService;
    private ArticleRepository articleRepository;
    private WordRepository wordRepository;
    private Set<Word> words;

    @Before
    public void setUp() {
        articleRepository = Mockito.mock(ArticleRepository.class);
        wordRepository = Mockito.mock(WordRepository.class);

        words = new HashSet<>();
        Word good = new Word("good");good.setId(0);
        Word practice = new Word("practice");practice.setId(1);

        words.add(good);words.add(practice);

        for (Word word : words) {
            when(wordRepository.findByName(word.name)).thenReturn(word);
        }

        articleService = new ArticleService(articleRepository, wordRepository);
    }

    @Test
    public void createArticle() throws Exception {
        Article article = new Article("A good practice when working with Spring", "Title");
        when(articleRepository.save(any(Article.class))).thenReturn(article);
        //TODO
        assertEquals(words, articleService.save(article).words);
    }

    @Test
    public void getArticleWordSimple(){
        Article original = new Article("The good", "Title");
        original.words = words;
        when(articleRepository.findOne(0L)).thenReturn(original);

        assertEquals("<p>The <span data-id=\"0\">good</span> </p>",
                articleService.findOne(0L).content.trim());
    }

    @Test
    public void getArticleWithWordWithDot(){
        Article original = new Article("The practice.", "Title");
        original.words = words;
        when(articleRepository.findOne(0L)).thenReturn(original);

        assertEquals("<p>The <span data-id=\"1\">practice.</span> </p>",
                articleService.findOne(0L).content.trim());
    }

    @Test
    public void getArticleWithoutWord(){
        Article original = new Article("The good practice.", "Title");
        original.words = new HashSet<>();
        when(articleRepository.findOne(0L)).thenReturn(original);

        assertEquals("<p>The good practice. </p>",
                articleService.findOne(0L).content.trim());
    }

}
