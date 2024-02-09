package com.fluentenglish.web.spacedrepetition.word.dto;

import com.fluentenglish.web.spacedrepetition.fsrs.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWordMemoDto {
    private int wordId;

    private int userId;

    private Grade grade;
}
