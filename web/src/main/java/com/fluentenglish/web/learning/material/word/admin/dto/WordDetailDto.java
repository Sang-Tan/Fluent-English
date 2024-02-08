package com.fluentenglish.web.learning.material.word.admin.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class WordDetailDto {
    private int id;

    private String text;

    private JsonNode sound;

    private JsonNode image;

    private String vietnameseMeaning;

    private int difficulty;

    private Set<Integer> lessonIds;
}
