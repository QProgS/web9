package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class Topic extends TextEntity{

    @Column(name="text", length = 20000)
    private String text;

    private String title;

    public Topic() {}

    public Topic(String text, String title) {
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




