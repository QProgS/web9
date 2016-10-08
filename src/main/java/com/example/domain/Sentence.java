package com.example.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sentence")
public class Sentence extends TextEntity{

    @Column(name="text", length = 500)
    private String text;

    public Sentence(){}

    public Sentence(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
