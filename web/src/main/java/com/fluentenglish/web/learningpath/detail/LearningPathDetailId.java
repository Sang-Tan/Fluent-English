package com.fluentenglish.web.learningpath.detail;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LearningPathDetailId implements Serializable {
    private Integer lessonId;
    private Integer learningPathId;
}
