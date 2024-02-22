package com.fluentenglish.web.learning.material.word.user;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.learning.material.word.ServiceWordMapper;
import com.fluentenglish.web.learning.material.word.Word;
import com.fluentenglish.web.learning.material.word.WordRepository;
import com.fluentenglish.web.learning.material.word.WordSpecifications;
import com.fluentenglish.web.learning.material.word.dto.WordDto;
import com.fluentenglish.web.learning.material.word.dto.WordSearchDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserWordService")
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    private final ServiceWordMapper wordMapper;

    private final WordSpecifications wordSpecifications;

    public WordServiceImpl(WordRepository wordRepository,
                           ServiceWordMapper wordMapper,
                           WordSpecifications wordSpecifications) {
        this.wordRepository = wordRepository;
        this.wordMapper = wordMapper;
        this.wordSpecifications = wordSpecifications;
    }

    @Override
    public WordDto getWordById(int wordId) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new NotFoundException("Word not found"));

        return wordMapper.toWordDto(word);
    }

    public List<WordDto> getWords(WordSearchDto wordSearchDto) {
        Specification<Word> specification = Specification.where(null);

        if (wordSearchDto.getText() != null && !wordSearchDto.getText().isEmpty()) {
            specification = specification.and(wordSpecifications.textContains(wordSearchDto.getText()));
        }

        if (wordSearchDto.getWordIds() != null && !wordSearchDto.getWordIds().isEmpty()) {
            specification = specification.and(wordSpecifications.wordIdsIn(wordSearchDto.getWordIds()));
        }

        List<Word> words = wordRepository.findAll(specification);

        return words.stream().map(wordMapper::toWordDto).toList();
    }

    @Override
    public List<WordDto> getWordsByLessonId(int lessonId) {
        List<Word> words = wordRepository.findAllByLessonId(lessonId);
        return words.stream().map(wordMapper::toWordDto).toList();
    }
}
