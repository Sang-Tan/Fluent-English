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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Introduction {
    @Id
    @Column(name = "lesson_id")
    private Integer lessonId;

    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
