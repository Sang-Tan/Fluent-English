package com.fluentenglish.web.learning.material.lesson;

import com.fluentenglish.web.learning.material.word.Word;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer difficulty;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "lessons__words",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private Set<Word> words;
}
