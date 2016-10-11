package com.example.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class TextEntity extends BaseEntity{

    @ManyToMany
    public Set<Word> words;

    public Date created;
    public Date lastModified;
    public long rating;

    public String getText() {
        return "";
    }
}
