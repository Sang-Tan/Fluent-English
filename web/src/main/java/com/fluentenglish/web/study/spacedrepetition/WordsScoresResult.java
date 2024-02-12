package com.fluentenglish.web.study.spacedrepetition;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class WordsScoresResult {
    /**
     * Key: wordId, Value: score
     */
    private Map<Integer, Integer> scoresByWords = new HashMap<>();
}
