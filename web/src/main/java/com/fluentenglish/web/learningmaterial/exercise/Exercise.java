package com.fluentenglish.web.learningmaterial.exercise;

import com.fluentenglish.web.learningmaterial.exercise.introduction.Introduction;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "exercises")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer position;

    @Column
    private String name;

    @Column(name = "core_skill")
    @Enumerated(EnumType.STRING)
    private CoreSkill coreSkill;

    private Byte difficulty;

    @Column
    @Builder.Default
    private boolean isPublic = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToOne(mappedBy = "exercise", fetch = FetchType.LAZY)
    private Introduction introduction;

    @OneToMany(mappedBy = "exercise")
    @OrderBy("position ASC")
    private List<Quiz> quizzes;
}
