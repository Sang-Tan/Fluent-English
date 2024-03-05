package com.fluentenglish.web.spacedrepetition.word.mapper;

import com.fluentenglish.web.spacedrepetition.word.WordMemo;
import com.fluentenglish.web.spacedrepetition.word.dto.status.StudyingWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class WordMemoMapperDecorator implements WordMemoMapper {
    private static final float STABILITY_FOR_MAX_PROFICIENCY = 365.0f;

    @Autowired
    @Qualifier("delegate")
    private WordMemoMapper delegate;

    @Override
    public StudyingWordDto toStudyingWordDto(WordMemo wordMemo) {
        StudyingWordDto wordDto = delegate.toStudyingWordDto(wordMemo);
        float proficiency = wordMemo.getStability() / STABILITY_FOR_MAX_PROFICIENCY;
        proficiency = Math.min(proficiency, 1.0f);
        wordDto.setProficiency(proficiency);

        return wordDto;
    }
}
