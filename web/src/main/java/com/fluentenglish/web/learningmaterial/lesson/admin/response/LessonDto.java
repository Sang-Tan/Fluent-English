package com.fluentenglish.web.learningmaterial.lesson.admin.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonDto {
    private int id;

    private String name;

    @JsonProperty("isPublic")
    private boolean isPublic;
}
