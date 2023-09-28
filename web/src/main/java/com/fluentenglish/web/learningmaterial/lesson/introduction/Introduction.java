package com.fluentenglish.web.learningmaterial.lesson.introduction;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "introductions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Introduction {
    @Id
    @Column(name = "lesson_id")
    private Integer lessonId;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
