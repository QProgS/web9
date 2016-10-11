package com.example.domain;

import com.example.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "word_definition")
public class WordDefinition extends TextEntity{

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @JsonView(Views.Public.class)
    @Column(name="text", length = 500)
    private String text;

    @JsonView(Views.Public.class)
    private String type;

    public WordDefinition(){}

    public WordDefinition(Word word, String text, String type, Date created, Date lastModified, long rating) {
        this.word = word;
        this.text = text;
        this.type = type;
        this.created = created;
        this.lastModified = lastModified;
        this.rating = rating;
    }

    public WordDefinition(Word word, String text, String type, Date date) {
        this(word, text, type, date, date, 0);
    }

    @Override
    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public Word getWord() {
        return word;
    }

    @Override
    public String toString() {
        return getText();
    }
}
