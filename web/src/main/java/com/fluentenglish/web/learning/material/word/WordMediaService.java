package com.fluentenglish.web.learning.material.word;

import com.fluentenglish.web.learning.material.word.admin.dto.WordCreateUpdateDto;

public interface WordMediaService {
    void saveMediaOnCreated(WordCreateUpdateDto wordCreateUpdateDto);
    void saveMediaOnUpdated(WordCreateUpdateDto wordCreateUpdateDto, Word persistedWord);
    void deleteMediaOnDeleted(Word word);
}
