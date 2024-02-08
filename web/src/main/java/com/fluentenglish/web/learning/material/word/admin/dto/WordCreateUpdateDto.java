package com.fluentenglish.web.learning.material.word.admin.dto;

import com.fluentenglish.web.common.fileresource.FileResourceDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

@Getter
@Setter
public class WordCreateUpdateDto {
    @NotBlank(message = "Text is mandatory")
    @Size(max = 100, message = "Text must not be longer than 100 characters")
    private String text;

    @NotNull
    private FileResourceDto sound;

    @NotNull
    private FileResourceDto image;

    @NotBlank(message = "Vietnamese meaning is mandatory")
    private String vietnameseMeaning;

    @Range(min = 1, max = 6, message = "Invalid difficulty")
    private int difficulty;

    private Set<Integer> lessonIds;
}
