package com.example.web;

import com.example.domain.Word;
import com.example.domain.WordDefinition;
import com.example.utils.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class WordsTests {

    private Word word;

    @Before
    public void initWord(){
        word = new Word("foo");
        word.setId(5);
        word.definitions = new ArrayList<>();
        word.definitions.add(new WordDefinition(word, "def1", "t1", new Date()));
        word.definitions.add(new WordDefinition(word, "def2", "t2", new Date()));
    }
    @Test
    public void whenUseJsonViewToSerialize_thenCorrect()
            throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String result = mapper.writerWithView(Views.Public.class)
                .writeValueAsString(word);

        assertThat(result, containsString("foo"));
        assertThat(result, containsString("def1"));
        assertThat(result, not(containsString("5")));
    }

    @Test
    public void whenUseJsonViewToSerialize_thenCorrect2()
            throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String result = mapper.writerWithView(Views.Public.class)
                .writeValueAsString(word);
    }
}
