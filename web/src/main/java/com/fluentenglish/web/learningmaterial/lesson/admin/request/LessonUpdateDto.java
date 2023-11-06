package com.fluentenglish.web.learningmaterial.lesson.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class LessonUpdateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not be longer than 100 characters")
    private String name;

    @NotBlank(message = "Please select a core skill")
    private String coreSkill;

    @Range(min = 1, max = 3, message = "Invalid difficulty")
    private int difficulty;
}
