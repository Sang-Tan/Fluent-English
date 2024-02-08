package com.fluentenglish.web.learning.material.word.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordDto {
    private int id;

    private String text;

    private JsonNode sound;

    private JsonNode image;

    private String vietnameseMeaning;

    private int difficulty;
}
