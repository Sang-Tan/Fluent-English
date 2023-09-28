package com.fluentenglish.web.learningmaterial.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@AllArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private boolean isPublic = false;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Lesson> lessons;
}
