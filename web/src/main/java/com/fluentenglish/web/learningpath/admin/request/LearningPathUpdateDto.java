package com.fluentenglish.web.learningpath.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearningPathUpdateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, min = 10, message = "Name must be between 10 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must not be more than 1000 characters")
    private String description;
}
