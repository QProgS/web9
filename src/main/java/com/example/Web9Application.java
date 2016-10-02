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
			try (BufferedReader br = new BufferedReader(new FileReader("F:\\Innk\\Dictionary in csv\\A.csv")))
			{
				String data;
				int kol = 0;
				while ((data = br.readLine()) != null && kol++ < 100) {
					data = data.replaceAll("'","''");
					if(data.length()<2)continue;
					int b =0, e = data.length();
					int sb = data.indexOf('('), se = data.indexOf(')');
					if(data.charAt(0) == '"'){
						b++;e--;
					}
					String name = data.substring(b,sb-1).toLowerCase();
					String type = data.substring(sb+1,Math.max(sb+1, se));
					String definition = data.substring(Math.min(se+2, e), e);

					Word word = wordRepository.findByName(name);
					if(word == null){
						word = new Word(name);
						wordRepository.save(word);
					}
                    Date date = new Date();
					WordDefinition wordDefinition = new WordDefinition(word,definition,type, date, date, 0);
					wordDefinitionRepository.save(wordDefinition);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


