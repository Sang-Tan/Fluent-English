package com.fluentenglish.web.study.session.service.quiz.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.fileresource.*;
import com.fluentenglish.web.learning.material.word.Word;
import com.fluentenglish.web.learning.material.word.WordRepository;
import com.fluentenglish.web.study.session.dao.quiz.Quiz;
import com.fluentenglish.web.study.session.dao.quiz.answer.input.InputAnswer;
import com.fluentenglish.web.study.session.dao.quiz.answer.multiplechoice.MultipleChoiceAnswer;
import com.fluentenglish.web.study.session.dao.quiz.answer.multiplechoice.choice.TextAnswerChoices;
import com.fluentenglish.web.study.session.dao.quiz.question.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizGenerateService {
    private static final int MAX_CHOICES = 4;

    private static final int MAX_TIME_QUIZ_SEC = 10;

    private final WordRepository wordRepository;

    private final ObjectMapper objectMapper;

    public QuizGenerateService(WordRepository wordRepository,
                               ObjectMapper objectMapper) {
        this.wordRepository = wordRepository;
        this.objectMapper = objectMapper;
    }

    public Quiz genListenQuesEngInputAnsQuiz(Integer wordId) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new NotFoundException("Word not found"));

        try {
            FileResourceDto attachment = objectMapper.readValue(word.getSound(), FileResourceDto.class);
            attachment.setMediaType(MediaType.AUDIO);
            Question question = new Question();
            question.setContent("Listen to the word and type it");
            question.setAttachment(attachment);

            InputAnswer answer = new InputAnswer();
            answer.setAnswer(word.getText());

            return new Quiz(wordId, question, answer, MAX_TIME_QUIZ_SEC);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Error reading sound data", e);
        }
    }

    public Quiz genVietTextQuesEngChoicesAnsQuiz(Integer wordId, Set<Integer> sessionWordIds) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new NotFoundException("Word not found"));

        Question question = new Question();
        question.setContent("What is the English meaning of '" + word.getVietnameseMeaning() + "'?");

        TextAnswerChoices choices = new TextAnswerChoices();
        Set<Integer> sessionWordIdsCopy = new HashSet<>(sessionWordIds);
        sessionWordIdsCopy.remove(wordId);
        // pick MAX_CHOICES - 1 word and make them be wrong choices
        List<Integer> randomIds = pickRandomWordIds(sessionWordIdsCopy, MAX_CHOICES - 1);
        List<String> wrongChoicesData = wordRepository.findAllById(randomIds).stream().map(Word::getText).toList();
        for (String data : wrongChoicesData) {
            choices.getData().add(data);
        }
        // add correct answer
        choices.getData().add(word.getText());
        Collections.shuffle(choices.getData());
        MultipleChoiceAnswer answer = new MultipleChoiceAnswer();

        answer.setChoices(choices);
        answer.setCorrectChoice(choices.getData().indexOf(word.getText()));

        return new Quiz(wordId, question, answer, MAX_TIME_QUIZ_SEC);
    }

    // if count > wordIds.size(), return all wordIds
    private List<Integer> pickRandomWordIds(Set<Integer> wordIds, int count) {
        List<Integer> shuffledList = new ArrayList<>(wordIds);
        Collections.shuffle(shuffledList);

        return shuffledList.subList(0, count);
    }
}
