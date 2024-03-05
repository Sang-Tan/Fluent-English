package com.fluentenglish.web.spacedrepetition.word;

import com.fluentenglish.web.spacedrepetition.word.dto.IgnoreWordsDto;
import com.fluentenglish.web.spacedrepetition.word.dto.UpdateWordMemoDto;
import com.fluentenglish.web.spacedrepetition.word.dto.status.WordStudyStatusDto;

import java.util.List;

public interface WordMemoService {
    void updateWordMemo(UpdateWordMemoDto updateWordMemoDto);

    /**
     * @return list of word ids that user need to study, including new words and words that need to be reviewed
     */
    List<Integer> getNeedToStudyWordIds(int userId, int limit);
    
    WordStudyStatusDto getWordStudyStatus(int userId, int wordId);

    List<WordStudyStatusDto> getWordsStudyStatus(int userId, List<Integer> wordIds);

    void ignoreWords(int userId, IgnoreWordsDto ignoreWordsDto);

    void reinstateWord(int userId, int wordId);
}
