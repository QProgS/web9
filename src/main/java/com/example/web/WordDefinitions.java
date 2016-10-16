package com.example.web;


import com.example.domain.WordDefinition;
import com.example.service.WordDefinitionRepository;
import com.example.utils.PageNav;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WordDefinitions {
    private static final String pagePath = "definitions";

    private WordDefinitionRepository wdRepository;

    @Autowired
    public WordDefinitions(WordDefinitionRepository wdRepository){
        this.wdRepository = wdRepository;
    }

    @RequestMapping(value = "/definitions", method = RequestMethod.GET)
    public ModelAndView showAll(Model model, @PageableDefault Pageable pageable) {

        PageNav<WordDefinition> definitionsPageNav = new PageNav<>(wdRepository.findAll(pageable), pageable, 7);

        return new ModelAndView("definitions")
                .addObject("definitions", definitionsPageNav)
                .addObject("pagePath", pagePath);
    }

}
