package com.example.service;

import com.example.domain.Article;
import com.example.domain.Word;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ArticleServiceTests {

    private ArticleService topicService;
    private ArticleRepository articleRepository;
    private WordRepository wordRepository;

    @Before
    public void setUp() {
        articleRepository = Mockito.mock(ArticleRepository.class);
        wordRepository = Mockito.mock(WordRepository.class);
        topicService = new ArticleService(articleRepository, wordRepository);
    }

    @Test
    public void createTopic() throws Exception {
        Article article = new Article("A good practice when working with Spring", "Title");
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        List<Word> words = new ArrayList<>();
        words.add(new Word("good"));words.add(new Word("practice"));
        for (Word word : words) {
            when(wordRepository.findByName(word.name)).thenReturn(word);
        }

        Article articleWithWords = topicService.save(article);

        assertEquals(articleWithWords.words,words);

    }



}
