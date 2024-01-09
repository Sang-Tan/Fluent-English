package com.fluentenglish.web.learningmaterial.lesson;

import com.fluentenglish.web.learningmaterial.exercise.Exercise;
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

    private String name;

    @Column
    @Builder.Default
    private boolean isPublic = false;

    @OneToMany(mappedBy = "lesson")
    @OrderBy("position ASC")
    private List<Exercise> exercises;
}
