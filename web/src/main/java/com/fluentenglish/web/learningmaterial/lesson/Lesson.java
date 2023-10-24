package com.fluentenglish.web.learningmaterial.lesson;

import com.fluentenglish.web.learningmaterial.lesson.introduction.Introduction;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.topic.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lesson {
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
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToOne(mappedBy = "lesson", fetch = FetchType.LAZY)
    private Introduction introduction;

    @OneToMany(mappedBy = "lesson")
    @OrderBy("position ASC")
    private List<Quiz> quizzes;
}
