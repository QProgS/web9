package com.example.service;

import com.example.domain.Word;
import org.hibernate.mapping.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WordRepository extends CrudRepository<Word, Long> {

    Page<Word> findAll(Pageable pageable);

    Page<Word> findByNameLike(String name, Pageable pageable);

    Word findByName(String name);
}
