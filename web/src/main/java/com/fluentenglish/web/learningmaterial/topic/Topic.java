package com.fluentenglish.web.learningmaterial.topic;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "topics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column
    @Builder.Default
    private boolean isPublic = false;

    @OneToMany(mappedBy = "topic")
    @OrderBy("position ASC")
    private List<Lesson> lessons;
}
