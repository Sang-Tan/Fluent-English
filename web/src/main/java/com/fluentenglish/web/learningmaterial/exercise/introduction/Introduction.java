package com.fluentenglish.web.learningmaterial.exercise.introduction;

import com.fluentenglish.web.learningmaterial.exercise.Exercise;
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
    @Column(name = "exercise_id")
    private Integer exerciseId;

    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
}
