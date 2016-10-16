package com.example.domain;

import com.example.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "word")
public class Word {

    @Id
    @GeneratedValue
    private long id;

    @JsonView(Views.Public.class)
    @Column(name = "name", unique = true, nullable = false)
    public String name;

    @JsonView(Views.Public.class)
    @OneToMany (mappedBy = "word")
    public List<WordDefinition> definitions;

    @ManyToMany
    public List<TextEntity> texts;

    public long rating;

    public Word(){}

    public Word(String name) {
        this.name = name.toLowerCase();
        definitions = new ArrayList<>();
        rating = 0;
    }

    public String getName() {
        return name;
    }

    public List<WordDefinition> getDefinitions() {
        return definitions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return name != null ? name.equals(word.name) : word.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
