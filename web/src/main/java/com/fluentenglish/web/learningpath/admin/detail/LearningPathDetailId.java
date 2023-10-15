package com.fluentenglish.web.learningpath.admin.detail;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LearningPathDetailId implements Serializable {
    private Integer lessonId;
    private Integer learningPathId;
}
