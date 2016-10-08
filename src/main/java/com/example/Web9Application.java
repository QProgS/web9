package com.example;

import com.example.domain.Word;
import com.example.domain.WordDefinition;
import com.example.service.WordDefinitionRepository;
import com.example.service.WordRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
public class Web9Application {

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private WordDefinitionRepository wordDefinitionRepository;

	public static void main(String[] args) {
		SpringApplication.run(Web9Application.class, args);
	}


	@Component
	public class MyRunner implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
            int kol = 0;
            for (char i = 'A'; i <= 'Z'; i++) {
                char filename = i;
                try (BufferedReader br = new BufferedReader(new FileReader("K:\\Innk\\wordsProject\\Dictionary in csv\\" + filename + ".csv")))
                {
                    String data;
                    while ((data = br.readLine()) != null && kol<1000) {
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
                        WordDefinition wordDefinition = new WordDefinition(word,definition,type, date, date, 0);
                        wordDefinitionRepository.save(wordDefinition);
                        kol++;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("filename: " + filename + " done.");
            }
            System.out.println("obj added: " + kol);

		}
	}
}


