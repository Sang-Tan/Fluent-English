package com.fluentenglish.web.learningmaterial.lesson.admin.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonDto {
    private int id;

    private int position;

    private String name;

    private String coreSkill;

    private int difficulty;

    private boolean isPublic;

    private int topicId;

    private String introductionContent;
}
