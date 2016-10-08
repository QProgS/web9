package com.example.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PageNav<T> extends PageImpl<T> {
    private List<Integer> pages = new ArrayList<>();

    public PageNav(Page<T> page, Pageable pageable, int maxNavSize) {
        super(page.getContent(), pageable, page.getTotalElements());
        //TODO do always maxNavSize
        int farDistance = maxNavSize/2;
        for (int i = Math.max(0,getNumber()-farDistance);
             i < Math.min(getTotalPages(),getNumber()+farDistance); i++) {
            pages.add(i);
        }
    }
    public PageNav(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
        for (int i = 0; i < getTotalPages()-1; i++) {
            pages.add(i);
        }
    }

    public PageNav(List<T> content) {
        super(content);
        for (int i = 0; i < getTotalPages()-1; i++) {
            pages.add(i);
        }
    }

    public List<Integer> getPages() {
        return pages;
    }
}
