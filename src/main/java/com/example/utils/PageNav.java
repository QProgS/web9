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
        int farDistance = (maxNavSize-1)/2;
        int maxPage = getTotalPages()-1;
        int addPage = 0;
        if(maxNavSize % 2 == 0)addPage = 1;

        int b = Math.max(0,getNumber()-farDistance);
        int e = Math.min(maxPage,getNumber()+farDistance + addPage);


        if(b == 0)e = Math.min(maxPage, maxNavSize-1);

        if(e == maxPage)b = Math.max(0,maxPage-(maxNavSize-1));

        for (int i = b; i <= e; i++) {
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
