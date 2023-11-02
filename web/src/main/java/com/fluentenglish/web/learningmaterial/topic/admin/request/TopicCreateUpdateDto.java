package com.fluentenglish.web.learningmaterial.topic.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicCreateUpdateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not be more than 100 characters")
    private String name;
}
