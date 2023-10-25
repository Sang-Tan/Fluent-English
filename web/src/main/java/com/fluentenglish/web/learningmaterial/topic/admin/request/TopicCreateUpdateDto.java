package com.fluentenglish.web.learningmaterial.topic.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicCreateUpdateDto {
    @NotBlank
    @Size(max = 100)
    private String name;
}
