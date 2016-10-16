package com.example.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class TextEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany
    public Set<Word> words;

    public Date created;
    public Date lastModified;
    public long rating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return "";
    }

}
