package com.fluentenglish.web.spacedrepetition.fsrs;

import lombok.Getter;

@Getter
public enum Grade {
    AGAIN(1),
    HARD(2),
    GOOD(3),
    EASY(4);

    private final int value;

    Grade(int value) {
        this.value = value;
    }
}
