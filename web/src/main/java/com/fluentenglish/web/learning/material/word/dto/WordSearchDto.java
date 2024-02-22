package com.fluentenglish.web.learning.material.word.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WordSearchDto {
    @Nullable
    private String text;

    @Nullable
    private List<Integer> wordIds;
}
