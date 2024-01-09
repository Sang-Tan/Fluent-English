package com.fluentenglish.web.learningmaterial.lesson.admin.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonDto {
    private int id;

    private String name;

    private boolean isPublic;
}
