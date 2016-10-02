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

import java.util.ArrayList;

@Controller
public class Words {

    private WordRepository wordRepository;

    @Autowired
    public Words(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }

    private static final String page = "words";

//    @GetMapping("/")
//    @ResponseBody
//    @Transactional
//    public String helloWorld() {
//        //wordRepository.save(new Word("test", "tt", ""));
//        return this.wordRepository.findAll().toString();
//    }

    @RequestMapping(value = "/words", method = RequestMethod.GET)
    public String helloWorld(Model model, Pageable pageable) {
        Page<Word> words = wordRepository.findAll(pageable);
        PageNav<Word> wordsPage= new PageNav<>(words.getContent(),pageable,words.getTotalElements(),8);

        model.addAttribute("words", wordsPage);
        model.addAttribute("page", page);
        return "words";
    }

    @RequestMapping(value = "/word/{id}", method = RequestMethod.GET)
    public String word(@PathVariable Long id, Model model) {
        Word word = wordRepository.findOne(id);
        if(word == null) return "error";
        model.addAttribute("word", word);
        model.addAttribute("page", "word");

        word.rating++;
        wordRepository.save(word);
        return "word";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, @RequestParam("query")String query, Pageable pageable) {
        Page<Word> words = wordRepository.findByNameLike(
                query.trim().toLowerCase()+"%",pageable);
        PageNav<Word> wordsPage= new PageNav<>(words.getContent(),pageable,words.getTotalElements(),8);

        model.addAttribute("words", wordsPage);
        model.addAttribute("page", "search");
        model.addAttribute("query", query);

        return "search";
    }
}
