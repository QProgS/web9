package com.example.service;

import com.example.domain.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TopicRepository extends CrudRepository<Topic, Long> {

}
