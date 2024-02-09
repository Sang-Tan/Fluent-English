package com.fluentenglish.web.spacedrepetition.word;

import com.fluentenglish.web.spacedrepetition.word.dto.UpdateWordMemoDto;

import java.util.List;

public interface WordMemoService {
    void updateWordMemo(UpdateWordMemoDto updateWordMemoDto);

    /**
     * @return list of word ids that user need to study, including new words and words that need to be reviewed
     */
    List<Integer> getNeedToStudyWordIds(int userId, int limit);
}
