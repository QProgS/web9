package com.example.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "word")
public class Word extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    public String name;

    @OneToMany (mappedBy = "word")
    private List<WordDefinition> definitions;

    @ManyToMany
    private List<TextEntity> texts;

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

    @Override
    public String toString() {
        return getName();
    }
}
