package com.fluentenglish.web.learning.material.lesson.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class LessonCreateUpdateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not be more than 100 characters")
    private String name;

    @Range(min = 1, max = 6, message = "Invalid difficulty")
    private int difficulty;
}
