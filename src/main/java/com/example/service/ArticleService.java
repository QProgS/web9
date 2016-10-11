package com.example.service;

import com.example.domain.Article;
import com.example.domain.Word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;


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
        article.words = new HashSet<>();
        Set<String> list = new HashSet<>(Arrays.asList(article.content.split("\\W+")));

        logger.info(list + " split list.");
        for (String item : list) {
        	List<String> wordForms = getWordForms(item);
        	Word word = null;
        	for(String name : wordForms){
        		word = wordRepository.findByName(name);
        		if(word != null) break;
        	}            
            if (word != null){
                article.words.add(word);
            }
        }
        logger.info(article.words.size() + " words were found.");
        return articleRepository.save(article);
    }

    public Article findOne(long id) {
        Article article = articleRepository.findOne(id);        

        List<String> list = new ArrayList<>(Arrays.asList(article.content.split("\\s")));

        StringBuilder sb = new StringBuilder();

        for (String item : list){
            long wordId = findItem(item,article.words);
            if(wordId != -1) {
                sb.append(processWord(wordId,item));
            } else {
                sb.append(item);
            }
            sb.append(' ');
        }

        article.setContent(sb.toString());
        return article;
    }

    private long findItem(String item, Set<Word> words) {
        for (Word word : words){
            List<String> wordForms = getWordForms(item);
            if(wordForms.contains(word.name)){
                return word.getId();
            }
        }
        return -1;
    }

    private List<String> getWordForms(String name) {
        List<String> a = new ArrayList<>();
        name = name.toLowerCase();
        //TODO        
        //char marks[] = {'.', ',', ';', ':'};
        //if(name.charAt(name.length()-1) )
        if(name.endsWith("."))name = name.substring(0,name.length()-1);
        if(name.endsWith(","))name = name.substring(0,name.length()-1);
        if(name.endsWith(":"))name = name.substring(0,name.length()-1);
        if(name.endsWith(";"))name = name.substring(0,name.length()-1);
        a.add(name);
        if(name.endsWith("s")) {
            a.add(name.substring(0, name.length() - 1));
        }
        if(name.endsWith("ing")) {
            a.add(name.substring(0, name.length() - 3));
        }
        return a;
    }

    private String processWord(long id, String name){
        return "<span data-id=\"" + id + "\">" + name + "</span>";
    }
}
