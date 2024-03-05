package com.fluentenglish.web.spacedrepetition.word.dto.status;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UncategorizedWordDto.class, name = "uncategorized"),
        @JsonSubTypes.Type(value = IgnoreWordDto.class, name = "ignore"),
        @JsonSubTypes.Type(value = StudyingWordDto.class, name = "studying")
})
public abstract class WordStudyStatusDto {
    private int wordId;
}
