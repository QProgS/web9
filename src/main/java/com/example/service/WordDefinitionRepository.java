package com.example.service;

import com.example.domain.WordDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WordDefinitionRepository  extends CrudRepository<WordDefinition, Long> {

    Page<WordDefinition> findAll(Pageable pageable);

    Page<WordDefinition> findByTextLike(String text, Pageable pageable);

}
