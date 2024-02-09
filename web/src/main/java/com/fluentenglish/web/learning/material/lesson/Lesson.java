package com.fluentenglish.web.learning.material.lesson;

import com.fluentenglish.web.learning.material.word.Word;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lessons")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @JdbcTypeCode(SqlTypes.SMALLINT)
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
