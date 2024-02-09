package com.fluentenglish.web.learning.material.word.admin;

import com.fluentenglish.web.learning.material.word.admin.dto.WordCreateUpdateDto;
import com.fluentenglish.web.learning.material.word.admin.dto.WordDetailDto;
import com.fluentenglish.web.learning.material.word.dto.WordDto;
import com.fluentenglish.web.learning.material.word.dto.WordSearchDto;

import java.util.List;

public interface WordService {
    Integer createWord(WordCreateUpdateDto wordCreateUpdateDto);

    WordDto getWordById(int wordId);

    WordDetailDto getWordDetailById(int wordId);

    List<WordDto> getWords(WordSearchDto search);

    void updateWord(int wordId, WordCreateUpdateDto wordCreateUpdateDto);

    void deleteWord(int wordId);

    List<WordDto> getWordsByLessonId(int lessonId);
}
