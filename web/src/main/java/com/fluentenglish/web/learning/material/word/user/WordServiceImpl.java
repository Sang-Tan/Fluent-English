package com.fluentenglish.web.learning.material.word.user;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.learning.material.word.ServiceWordMapper;
import com.fluentenglish.web.learning.material.word.Word;
import com.fluentenglish.web.learning.material.word.WordRepository;
import com.fluentenglish.web.learning.material.word.dto.WordDto;
import com.fluentenglish.web.learning.material.word.dto.WordSearchDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserWordService")
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    private final ServiceWordMapper wordMapper;

    public WordServiceImpl(WordRepository wordRepository, ServiceWordMapper wordMapper) {
        this.wordRepository = wordRepository;
        this.wordMapper = wordMapper;
    }

    @Override
    public WordDto getWordById(int wordId) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new NotFoundException("Word not found"));

        return wordMapper.toWordDto(word);
    }

    public List<WordDto> getWords(WordSearchDto wordSearchDto) {
        List<Word> words = wordRepository.getWords(wordSearchDto);
        return words.stream().map(wordMapper::toWordDto).toList();
    }

    @Override
    public List<WordDto> getWordsByLessonId(int lessonId) {
        List<Word> words = wordRepository.findAllByLessonId(lessonId);
        return words.stream().map(wordMapper::toWordDto).toList();
    }
}
