package com.fluentenglish.web.learning.material.word.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.learning.material.lesson.LessonRepository;
import com.fluentenglish.web.learning.material.word.ServiceWordMapper;
import com.fluentenglish.web.learning.material.word.Word;
import com.fluentenglish.web.learning.material.word.WordMediaService;
import com.fluentenglish.web.learning.material.word.WordRepository;
import com.fluentenglish.web.learning.material.word.dto.WordCreateUpdateDto;
import com.fluentenglish.web.learning.material.word.dto.WordDetailDto;
import com.fluentenglish.web.learning.material.word.dto.WordDto;
import com.fluentenglish.web.learning.material.lesson.Lesson;
import com.fluentenglish.web.learning.material.word.dto.WordSearchDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("AdminWordService")
public class WordServiceImpl implements WordService {
    private final WordMediaService wordMediaService;

    private final WordRepository wordRepository;

    private final LessonRepository lessonRepository;

    private final ServiceWordMapper wordMapper;

    public WordServiceImpl(WordMediaService wordMediaService, WordRepository wordRepository, LessonRepository lessonRepository, ServiceWordMapper wordMapper) {
        this.wordMediaService = wordMediaService;
        this.wordRepository = wordRepository;
        this.lessonRepository = lessonRepository;
        this.wordMapper = wordMapper;
    }

    @Override
    public Integer createWord(WordCreateUpdateDto wordCreateUpdateDto) {
        if(wordRepository.existsByText(wordCreateUpdateDto.getText())) {
            throw new UserInputException("This word already exists");
        }

        Set<Lesson> lessons = new HashSet<>();
        for (Integer lessonId : wordCreateUpdateDto.getLessonIds()) {
            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));
            if(wordRepository.existsByWordAndLessonId(wordCreateUpdateDto.getText(), lessonId)) {
                throw new UserInputException("This word already exists in this lesson");
            }
            lessons.add(lesson);
        }

        Word word = wordMapper.toWord(wordCreateUpdateDto);
        word.setLessons(lessons);

        Integer wordId = wordRepository.save(word).getId();
        wordMediaService.saveMediaOnCreated(wordCreateUpdateDto);
        return wordId;
    }

    @Override
    public WordDto getWordById(int wordId) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new NotFoundException("Word not found"));

        return wordMapper.toWordDto(word);
    }

    @Override
    public WordDetailDto getWordDetailById(int wordId) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new NotFoundException("Word not found"));

        return wordMapper.toWordDetailDto(word);
    }

    @Override
    public List<WordDto> getWords(WordSearchDto search) {
        List<Word> words = wordRepository.getWords(search);
        return words.stream().map(wordMapper::toWordDto).toList();
    }

    @Override
    public void updateWord(int wordId, WordCreateUpdateDto wordCreateUpdateDto) {
        Word word = wordRepository.findById(wordId).orElseThrow(() ->
                new UserInputException("The word you are trying to update does not exist"));

        wordMediaService.saveMediaOnUpdated(wordCreateUpdateDto, word);

        wordMapper.updateWord(wordCreateUpdateDto, word);

        Set<Lesson> lessons = new HashSet<>();
        for (Integer lessonId : wordCreateUpdateDto.getLessonIds()) {
            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));
            lessons.add(lesson);
        }
        word.setLessons(lessons);

        wordRepository.save(word);
    }

    @Override
    public void deleteWord(int wordId) {
        if(!wordRepository.existsById(wordId)) {
            throw new NotFoundException("Word not found");
        }
        wordRepository.deleteById(wordId);

        if (!wordRepository.existsById(wordId)) {
            return;
        }

        Word word = wordRepository.getReferenceById(wordId);

        wordRepository.delete(word);
        wordMediaService.deleteMediaOnDeleted(word);
    }

    @Override
    public List<WordDto> getWordsByLessonId(int lessonId) {
        List<Word> words = wordRepository.findAllByLessonId(lessonId);
        return words.stream().map(wordMapper::toWordDto).toList();
    }
}
