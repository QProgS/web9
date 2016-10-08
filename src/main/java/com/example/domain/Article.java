package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class Article extends TextEntity{

    @Column(name="text", length = 20000)
    public String text;

    @Column(name="text", length = 500)
    public String title;

    public Article() {}

    public Article(String text, String title) {
        this.text = text;
        this.title = title;
    }

    @Override
    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}




