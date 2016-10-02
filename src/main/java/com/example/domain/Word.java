package com.example.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "word")
public class Word {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany (mappedBy = "word")
    private List<WordDefinition> definitions;

    public long rating;

    public Word(){}

    public Word(String name) {
        this.name = name.toLowerCase();
        definitions = new ArrayList<>();
        rating = 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<WordDefinition> getDefinitions() {
        return definitions;
    }

    @Override
    public String toString() {
        return getName();
    }
}
