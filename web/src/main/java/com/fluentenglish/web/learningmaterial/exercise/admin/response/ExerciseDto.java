package com.fluentenglish.web.learningmaterial.exercise.admin.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDto {
    private int id;

    private int position;

    private String name;

    private String skill;

    private int difficulty;

    @JsonProperty("isPublic")
    private boolean isPublic;

    private int lessonId;

    private String introduction;
}
