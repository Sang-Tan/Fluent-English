package com.fluentenglish.web.learningpath.admin.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearningPathDto {
    private int id;
    private String name;
    private String description;
    private Boolean isPublic;
}
