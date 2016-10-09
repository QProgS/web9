package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "article")
public class Article extends TextEntity{

    @NotNull
    @Size(min=3, max=20000)
    @Column(name="content", length = 20000)
    public String content;

    @NotNull
    @Size(min=3, max=500)
    @Column(name="title", length = 500)
    public String title;

    public Article() {}

    public Article(String content, String title) {
        this.content = content;
        this.title = title;
    }

    @Override
    public String getText() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}




