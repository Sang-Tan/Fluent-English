package com.fluentenglish.web.learning.material.word;

import com.fluentenglish.web.learning.material.lesson.Lesson;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "words")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String text;

    @JdbcTypeCode(SqlTypes.JSON)
    private String sound;

    @JdbcTypeCode(SqlTypes.JSON)
    private String image;

    private String vietnameseMeaning;

    @JdbcTypeCode(SqlTypes.SMALLINT)
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

    public Word(int wordId) {
        this.id = wordId;
    }
}
