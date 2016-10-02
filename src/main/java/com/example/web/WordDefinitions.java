package com.example.web;

import com.example.domain.Word;
import com.example.domain.WordDefinition;
import com.example.service.WordDefinitionRepository;
import com.example.utils.PageNav;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WordDefinitions {

    @Autowired
    private WordDefinitionRepository wdRepository;

    private static final String page = "definitions";

    @RequestMapping(value = "/definitions", method = RequestMethod.GET)
    public String helloWorld(Model model, Pageable pageable) {
        Page<WordDefinition> definitions = wdRepository.findAll(pageable);
        PageNav<WordDefinition> definitionsPage= new PageNav<>(definitions.getContent(),pageable,definitions.getTotalElements(),8);

        model.addAttribute("definitions", definitionsPage);
        model.addAttribute("page", page);
        return "definitions";
    }

//    @Transactional
//    @RequestMapping(value = "/searchd", method = RequestMethod.POST)
//    public String search(Model model, @RequestParam("query")String query, Pageable pageable) {
//        Page<WordDefinition> definitions = wdRepository.findByTextLike("%"+query+"%",pageable);
//
//        PageNav<WordDefinition> wordsPage= new PageNav<>(definitions.getContent(),pageable,definitions.getTotalElements(),8);
//
//        model.addAttribute("words", wordsPage);
//        model.addAttribute("page", "search");
//        model.addAttribute("query", query);
//        model.addAttribute("resultCount", wordsPage.getTotalElements());
//
//        return "search";
//    }

//    @Transactional
//    @RequestMapping(value = "/definition/{id}", method = RequestMethod.GET)
//    public String word(@PathVariable Long id, Model model) {
//        WordDefinition definition = wdRepository.findOne(id);
//        if(definition == null) return "error";
//        Hibernate.initialize(definition.getText());
//        model.addAttribute("word", definition);
//        model.addAttribute("page", "word");
//
//        return "word";
//    }
}
