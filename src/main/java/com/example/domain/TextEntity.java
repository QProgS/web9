package com.example.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class TextEntity extends BaseEntity{

    @ManyToMany
    private List<Word> words;

    Date created;
    Date lastModified;
    long rating;

    public String getText() {
        return "";
    }
    public Date getCreated() {
        return created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public List<Word> getWords() {
        return words;
    }

    public long getRating() {
        return rating;
    }
}
