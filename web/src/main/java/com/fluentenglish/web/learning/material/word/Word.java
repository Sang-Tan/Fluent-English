package com.fluentenglish.web.learning.material.word;

import com.fluentenglish.web.learning.material.lesson.Lesson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue
    private Integer id;

    private String text;

    @JdbcTypeCode(SqlTypes.JSON)
    private String sound;

    @JdbcTypeCode(SqlTypes.JSON)
    private String image;

    private String vietnameseMeaning;

    private Integer difficulty;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "lessons__words",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson> lessons;

}
