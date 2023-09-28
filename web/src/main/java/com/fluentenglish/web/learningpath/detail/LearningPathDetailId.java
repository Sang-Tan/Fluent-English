package com.fluentenglish.web.learningpath.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LearningPathDetailId implements Serializable {
    private Integer topicId;
    private Integer learningPathId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LearningPathDetailId)) return false;
        LearningPathDetailId that = (LearningPathDetailId) o;
        return Objects.equals(topicId, that.topicId) &&
                Objects.equals(learningPathId, that.learningPathId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, learningPathId);
    }
}
