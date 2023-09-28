package com.fluentenglish.web.learningmaterial.lesson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fluentenglish.web.learningmaterial.lesson.introduction.Introduction;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.topic.Topic;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lessons",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"topic_id", "position"}),
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer position;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(name = "core_skill", nullable = false)
    @Enumerated(EnumType.STRING)
    private CoreSkill coreSkill;
    @Min(1)
    @Max(3)
    private Byte difficulty;
    @Column(nullable = false)
    private boolean isPublic = false;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Introduction introduction;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Quiz> quizzes;
}
