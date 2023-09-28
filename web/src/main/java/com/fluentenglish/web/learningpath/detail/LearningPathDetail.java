package com.fluentenglish.web.learningpath.detail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fluentenglish.web.learningmaterial.topic.Topic;
import com.fluentenglish.web.learningpath.LearningPath;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic_and_learning_path")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningPathDetail {
    @EmbeddedId
    private LearningPathDetailId id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @MapsId("topicId")
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @MapsId("learningPathId")
    @JoinColumn(name = "learning_path_id", nullable = false)
    @JsonIgnore
    private LearningPath learningPath;
    @Column(nullable = false)
    private Integer position;

}
