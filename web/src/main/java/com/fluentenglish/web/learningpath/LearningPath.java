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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LearningPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Builder.Default
    private boolean isPublic = false;

    @OneToMany(mappedBy = "learningPath")
    @OrderBy("position ASC")
    private List<LearningPathDetail> learningPathDetails;
}
