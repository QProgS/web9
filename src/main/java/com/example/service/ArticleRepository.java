package com.example.service;

import com.example.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ArticleRepository extends CrudRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);
}
