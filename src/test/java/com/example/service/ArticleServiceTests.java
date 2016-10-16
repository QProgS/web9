package com.example.service;

import com.example.domain.Article;
import com.example.domain.Word;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
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
        Article article = new Article("Title", "A good practice when working with Spring");
        when(articleRepository.save(any(Article.class))).thenReturn(article);
        //TODO
        assertEquals(words, articleService.save(article).words);
    }

    @Test
    public void getArticleWordSimple(){
        Article original = new Article("Title", "The good");
        original.words = words;
        when(articleRepository.findOne(0L)).thenReturn(original);

        assertEquals("<p>The <span data-id=\"0\">good</span> </p>",
                articleService.findOne(0L).content.trim());
    }

    @Test
    public void getArticleWithWordWithDot(){
        Article original = new Article("Title", "The practice.");
        original.words = words;
        when(articleRepository.findOne(0L)).thenReturn(original);

        assertEquals("<p>The <span data-id=\"1\">practice.</span> </p>",
                articleService.findOne(0L).content.trim());
    }

    @Test
    public void getArticleWithoutWord(){
        Article original = new Article("Title", "The good practice.");
        original.words = new HashSet<>();
        when(articleRepository.findOne(0L)).thenReturn(original);

        assertEquals("<p>The good practice. </p>",
                articleService.findOne(0L).content.trim());
    }

    @Test
    public void testTest(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            Article[] articles = mapper.readValue(new File("C:\\java\\wordsProject\\Dictionary in csv\\articles.json"),
                    Article[].class);
            for(Article article : articles){
                articleService.save(article);
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
