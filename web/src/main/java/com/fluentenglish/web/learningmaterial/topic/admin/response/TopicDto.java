package com.fluentenglish.web.learningmaterial.topic.admin.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicDto {
    private int id;

    private String name;

    private boolean isPublic;
}
