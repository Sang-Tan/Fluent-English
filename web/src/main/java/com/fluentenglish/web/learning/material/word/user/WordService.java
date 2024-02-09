package com.fluentenglish.web.learning.material.word.user;

import com.fluentenglish.web.learning.material.word.dto.WordDto;
import com.fluentenglish.web.learning.material.word.dto.WordSearchDto;

import java.util.List;

public interface WordService {
    WordDto getWordById(int wordId);

    List<WordDto> getWords(WordSearchDto wordSearchDto);

    List<WordDto> getWordsByLessonId(int lessonId);
}
