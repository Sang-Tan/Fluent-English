package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.AnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.AnswerMediaExtractor;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.MediaDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.question.QuestionDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.question.QuestionMediaExtractor;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import com.fluentenglish.web.upload.cloud.StorageExecutorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizMediaServiceImpl implements QuizMediaService {
    private final QuestionMediaExtractor questionMediaExtractor;

    private final AnswerMediaExtractor answerMediaExtractor;

    private final ObjectMapper objectMapper;

    private final StorageExecutorService storageExecutorService;

    public QuizMediaServiceImpl(QuestionMediaExtractor questionMediaExtractor,
                                AnswerMediaExtractor answerMediaExtractor,
                                ObjectMapper objectMapper,
                                StorageExecutorService storageExecutorService) {
        this.questionMediaExtractor = questionMediaExtractor;
        this.answerMediaExtractor = answerMediaExtractor;
        this.objectMapper = objectMapper;
        this.storageExecutorService = storageExecutorService;
    }

    @Override
    public void saveMediaOnCreated(QuizCreateDto quizCreateDto) {
        getMediaIds(quizCreateDto.getQuestion(), quizCreateDto.getAnswer())
                .forEach(storageExecutorService::makeFilePermanent);
    }

    @Override
    public void saveMediaOnUpdated(QuizUpdateDto quizUpdateDto, Quiz persistedQuiz) {
        List<String> updatedMediaIds =
                getMediaIds(quizUpdateDto.getQuestion(), quizUpdateDto.getAnswer());
        Set<String> mediaIdsToRemove = new HashSet<>(getMediaIdsFromQuiz(persistedQuiz));

        List<String> mediaIdsToSave = new ArrayList<>();
        updatedMediaIds.forEach(mediaId -> {
            if (!mediaIdsToRemove.contains(mediaId)) {
                mediaIdsToSave.add(mediaId);
            } else {
                mediaIdsToRemove.remove(mediaId);
            }
        });

        mediaIdsToSave.forEach(storageExecutorService::makeFilePermanent);
        mediaIdsToRemove.forEach(storageExecutorService::deleteFile);
    }

    @Override
    public void deleteMediaOnDeleted(Quiz quiz) {
        getMediaIdsFromQuiz(quiz).forEach(storageExecutorService::deleteFile);
    }

    private List<String> getMediaIdsFromQuiz(Quiz quiz) {
        try {
            QuestionDto question = objectMapper.readValue(quiz.getQuestion(), QuestionDto.class);
            AnswerDto answer = objectMapper.readValue(quiz.getAnswer(), AnswerDto.class);

            return getMediaIds(question, answer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getMediaIds(QuestionDto question, AnswerDto answer) {
        List<String> mediaIds = new ArrayList<>();
        mediaIds.addAll(questionMediaExtractor.extractMedia(question)
                .stream()
                .map(MediaDto::getId)
                .toList());
        mediaIds.addAll(answerMediaExtractor.extractMedia(answer)
                .stream()
                .map(MediaDto::getId)
                .toList());

        return mediaIds;
    }
}
