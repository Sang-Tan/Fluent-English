package com.fluentenglish.web.learningmaterial.exercise.admin.response;

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

    private boolean isPublic;

    private int lessonId;

    private String introduction;
}
