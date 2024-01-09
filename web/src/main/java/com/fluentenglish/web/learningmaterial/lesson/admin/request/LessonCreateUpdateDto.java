package com.fluentenglish.web.learningmaterial.lesson.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonCreateUpdateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not be more than 100 characters")
    private String name;
}
