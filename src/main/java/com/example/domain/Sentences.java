package com.example.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sentences")
public class Sentences extends TextEntity{

    @Column(name="text", length = 1000)
    private String text;

    public Sentences(){}

    public Sentences(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
