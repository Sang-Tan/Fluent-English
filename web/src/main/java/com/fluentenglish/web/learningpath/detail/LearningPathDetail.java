package com.fluentenglish.web.learningpath.detail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningpath.LearningPath;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lesson_and_learning_path")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LearningPathDetail {
    @EmbeddedId
    private LearningPathDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("learningPathId")
    @JoinColumn(name = "learning_path_id")
    @JsonIgnore
    private LearningPath learningPath;

    @Column
    private Integer position;
}
