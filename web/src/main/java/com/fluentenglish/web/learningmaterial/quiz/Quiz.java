package com.fluentenglish.web.learningmaterial.quiz;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quizzes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"lesson_id", "position"}),
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer position;
    @Column(columnDefinition = "JSONB", nullable = false)
    private String question;
    @Column(columnDefinition = "JSONB", nullable = false)
    private String answer;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
}
