package com.fluentenglish.web.learningmaterial.quiz;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer position;

    @Column(columnDefinition = "JSONB")
    private String question;

    @Column(columnDefinition = "JSONB")
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
}
