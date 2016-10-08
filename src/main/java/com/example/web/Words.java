package com.example.web;

import com.example.domain.Word;
import com.example.service.WordRepository;
import com.example.utils.PageNav;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class Words {
    private static final String pagePath = "words";

    private WordRepository wordRepository;

    @Autowired
    public Words(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }


    @RequestMapping(value = "/words", method = RequestMethod.GET)
    public ModelAndView showWords(Pageable pageable) {

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 8);

        return new ModelAndView("words")
                .addObject("words", wordsPageNav)
                .addObject("pagePath", pagePath);
    }

    @RequestMapping(value = "/word/{id}", method = RequestMethod.GET)
    public ModelAndView word(@PathVariable Long id, Model model) {
        Word word = wordRepository.findOne(id);
        if(word == null) return new ModelAndView("error");

        return new ModelAndView("word")
                .addObject("word", word)
                .addObject("pagePath", "word");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value = "query", defaultValue = "", required = false)String query,
                               Pageable pageable) {

        Page<Word> words = wordRepository.findByNameLike(query.trim().toLowerCase()+"%",pageable);

        return new ModelAndView("search")
                .addObject("words", words)
                .addObject("pagePath", "search")
                .addObject("query", query);
    }
}
