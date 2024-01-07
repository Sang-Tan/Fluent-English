package com.fluentenglish.web.learningpath.admin.detail;

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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LearningPathDetail {
    @EmbeddedId
    private LearningPathDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("topicId")
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("learningPathId")
    @JoinColumn(name = "learning_path_id")
    @JsonIgnore
    private LearningPath learningPath;

    @Column
    private Integer position;
}
