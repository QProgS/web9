package com.example.service;

import com.example.domain.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
