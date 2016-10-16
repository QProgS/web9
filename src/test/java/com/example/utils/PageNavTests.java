package com.example.utils;

import com.example.domain.Word;
import com.example.service.WordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PageNavTests {

    private WordRepository wordRepository;
    private Pageable pageable;

    @Before
    public void setUp() {
        wordRepository = Mockito.mock(WordRepository.class);
    }

    private void mockWithPage(PageRequest pageable){
        this.pageable = pageable;
        final int totalItemCount = 100;

        List<Word> content = IntStream.range(0,totalItemCount)
                .mapToObj( s -> new Word("word " + s + pageable.first()) )
                .collect(Collectors.toList());

        Page<Word> page = new PageImpl<>(content, pageable, totalItemCount);
        when(wordRepository.findAll(pageable)).thenReturn(page);
    }



    @Test
    public void noTouchBorderEvenPages() {
        mockWithPage(new PageRequest(5,10));

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 6);

        assertEquals(6, wordsPageNav.getPages().size());

        assertEquals(3 , (int)wordsPageNav.getPages().get(0));
        assertEquals(8 , (int)wordsPageNav.getPages().get( wordsPageNav.getPages().size()-1));
    }

    @Test
    public void noTouchBorderOddPages(){
        mockWithPage(new PageRequest(5,10));

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 5);

        assertEquals(5 , wordsPageNav.getPages().size());

        assertEquals(3 , (int)wordsPageNav.getPages().get(0));
        assertEquals(7 , (int)wordsPageNav.getPages().get( wordsPageNav.getPages().size()-1));
    }

    @Test
    public void LeftBorderOddPages(){
        mockWithPage(new PageRequest(0,10));

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 5);

        assertEquals(5 , wordsPageNav.getPages().size());
    }

    @Test
    public void LeftBorderEvenPages(){
        mockWithPage(new PageRequest(0,10));

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 6);

        assertEquals(6 , wordsPageNav.getPages().size());
    }

    @Test
    public void RightBorderOddPages(){
        mockWithPage(new PageRequest(9,10));

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 5);

        assertEquals(5 , wordsPageNav.getPages().size());
    }

    @Test
    public void RightBorderEvenPages(){
        mockWithPage(new PageRequest(9,10));

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 6);

        assertEquals(6 , wordsPageNav.getPages().size());
    }


    private void mockWithPageMin(PageRequest pageable, int totalItemCount){
        this.pageable = pageable;

        List<Word> content = IntStream.range(0, totalItemCount - pageable.getPageNumber()*pageable.getPageSize())
                .mapToObj( s -> new Word("word " + s + pageable.first()) )
                .collect(Collectors.toList());

        Page<Word> page = new PageImpl<>(content, pageable, totalItemCount);
        when(wordRepository.findAll(pageable)).thenReturn(page);
    }

    @Test
    public void min2() {
        mockWithPageMin(new PageRequest(2,10), 24);

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 6);

        assertEquals(3, wordsPageNav.getPages().size());

        assertEquals(0 , (int)wordsPageNav.getPages().get(0));
        assertEquals(2 , (int)wordsPageNav.getPages().get( wordsPageNav.getPages().size()-1));
    }

    @Test
    public void min1() {
        mockWithPageMin(new PageRequest(1,10), 24);

        PageNav<Word> wordsPageNav = new PageNav<>(wordRepository.findAll(pageable), pageable, 6);

        assertEquals(3, wordsPageNav.getPages().size());

        assertEquals(0 , (int)wordsPageNav.getPages().get(0));
        assertEquals(2 , (int)wordsPageNav.getPages().get( wordsPageNav.getPages().size()-1));
    }

}
