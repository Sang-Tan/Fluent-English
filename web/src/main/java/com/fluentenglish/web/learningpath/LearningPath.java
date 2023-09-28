package com.fluentenglish.web.learningpath;

import com.fluentenglish.web.learningpath.detail.LearningPathDetail;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "learning_paths")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100, unique = true, nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean isPublic = false;
    @OneToMany(mappedBy = "learningPath", cascade = CascadeType.REMOVE)
    private List<LearningPathDetail> learningPathDetails;
}
