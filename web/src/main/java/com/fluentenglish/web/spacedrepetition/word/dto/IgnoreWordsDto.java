package com.fluentenglish.web.spacedrepetition.word.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class IgnoreWordsDto {
    private Set<Integer> wordIds;
}
