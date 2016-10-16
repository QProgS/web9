package com.example;

import com.example.domain.Article;
import com.example.domain.Word;
import com.example.domain.WordDefinition;
import com.example.service.ArticleService;
import com.example.service.WordDefinitionRepository;
import com.example.service.WordRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;

@SpringBootApplication
@ComponentScan("com.example")
public class Web9Application {

    public static void main(String[] args) {
        SpringApplication.run(Web9Application.class, args);
    }

    private WordRepository wordRepository;
    private WordDefinitionRepository wordDefinitionRepository;
    private ArticleService articleService;

    @Autowired
    public Web9Application(WordRepository wordRepository,
                           WordDefinitionRepository wordDefinitionRepository,
                           ArticleService articleService) {
        this.wordRepository = wordRepository;
        this.wordDefinitionRepository = wordDefinitionRepository;
        this.articleService = articleService;
    }

    //@Component
    public class MyRunner implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            int kol = 0;
            for (char i = 'A'; i <= 'Z'; i++) {
                char filename = i;
                try (BufferedReader br = new BufferedReader(new FileReader("C:\\java\\wordsProject\\Dictionary in csv\\" + filename + ".csv")))
                {
                    String data;
                    while ((data = br.readLine()) != null) {
                        data = data.replaceAll("'","''");
                        if(data.length()<2)continue;
                        int b =0, e = data.length();
                        int sb = data.indexOf('('), se = data.indexOf(')');
                        if(data.charAt(0) == '"'){
                            b++;e--;
                        }
                        String name,definition,type;
                        if(sb != -1 && se != -1) {
                            name = data.substring(b, sb - 1).toLowerCase();
                            type = data.substring(sb + 1, Math.max(sb + 1, se));
                            definition = data.substring(Math.min(se + 2, e), e);
                        }else{
                            int sp =  data.indexOf(' ');
                            name = data.substring(b, sp - 1).toLowerCase();
                            definition = data.substring(sp, e);
                            type = "";
                        }
                        Word word = wordRepository.findByName(name);
                        if(word == null){
                            word = new Word(name);
                            wordRepository.save(word);
                        }
                        Date date = new Date();
                        if(definition.length() < 450) {
                            WordDefinition wordDefinition = new WordDefinition(word, definition, type, date, date, 0);
                            wordDefinitionRepository.save(wordDefinition);
                        }
                        kol++;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("filename: " + filename + " done.");
            }

            System.out.println("obj added: " + kol);

            try {
                ObjectMapper mapper = new ObjectMapper();

                Article[] articles = mapper.readValue(new File("C:\\java\\wordsProject\\articles.json"),
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
}


