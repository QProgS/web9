package com.example.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table (name = "word_definition")
public class WordDefinition {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @Column(name="text", length = 2000)
    private String text;

    private String type;
    private Date created;
    private Date lastModified;
    public long rating;

    public WordDefinition(){}

    public WordDefinition(Word word, String text, String type, Date created, Date lastModified, long rating) {
        this.word = word;
        this.text = text;
        this.type = type;
        this.created = created;
        this.lastModified = lastModified;
        this.rating = rating;
    }

    public Date getCreated() {
        return created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public long getId() {
        return id;
    }

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
