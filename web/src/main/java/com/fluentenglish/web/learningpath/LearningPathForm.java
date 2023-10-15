package com.fluentenglish.web.learningpath;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LearningPathForm {
    private String name;
    private String description;
    private Boolean isPublic;
    private List<Integer> topicIds;
}
