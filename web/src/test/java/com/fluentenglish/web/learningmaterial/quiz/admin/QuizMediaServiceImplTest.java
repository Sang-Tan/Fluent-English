package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.AnswerMediaExtractor;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.MediaDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.question.QuestionDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.question.QuestionMediaExtractor;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import com.fluentenglish.web.upload.cloud.StorageExecutorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class QuizMediaServiceImplTest {

    @InjectMocks
    QuizMediaServiceImpl quizMediaService;

    @Mock
    QuestionMediaExtractor questionMediaExtractor;

    @Mock
    AnswerMediaExtractor answerMediaExtractor;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    StorageExecutorService storageExecutorService;

    @Test
    void saveMediaOnCreated_shouldSaveExtractedMediaIds() {
        List<MediaDto> mediaDtos = createMediaDtos(List.of("0", "1", "2"));
        when(questionMediaExtractor.extractMedia(any())).thenReturn(mediaDtos);

        quizMediaService.saveMediaOnCreated(new QuizCreateDto());

        verify(storageExecutorService).makeFilePermanent("0");
        verify(storageExecutorService).makeFilePermanent("1");
        verify(storageExecutorService).makeFilePermanent("2");
    }

    @Test
    void saveMediaOnUpdated_shouldSaveNewAndRemoveDeletedMedia() {
        // given
        List<MediaDto> updatedMediaDtos = createMediaDtos(List.of("0", "1", "2"));
        List<MediaDto> persistedMediaDtos = createMediaDtos(List.of("2", "3", "4"));

        QuizUpdateDto quizUpdateDto = new QuizUpdateDto();
        quizUpdateDto.setQuestion(new QuestionDto());
        when(questionMediaExtractor.extractMedia(quizUpdateDto.getQuestion()))
                .thenReturn(updatedMediaDtos);

        QuestionDto persistedQuestionDto = new QuestionDto();
        when(objectMapper.convertValue(any(), eq(QuestionDto.class)))
                .thenReturn(persistedQuestionDto);
        when(questionMediaExtractor.extractMedia(persistedQuestionDto))
                .thenReturn(persistedMediaDtos);

        // when
        quizMediaService.saveMediaOnUpdated(quizUpdateDto, new Quiz());

        // then
        verify(storageExecutorService).makeFilePermanent("0");
        verify(storageExecutorService).makeFilePermanent("1");
        verify(storageExecutorService).deleteFile("3");
        verify(storageExecutorService).deleteFile("4");
    }

    @Test
    void deleteMediaOnDeleted_shouldDeleteMedia() {
        // given
        List<MediaDto> persistedMediaDtos = createMediaDtos(List.of("0", "1", "2"));

        Quiz quiz = new Quiz();
        QuestionDto persistedQuestionDto = new QuestionDto();
        when(objectMapper.convertValue(any(), eq(QuestionDto.class)))
                .thenReturn(persistedQuestionDto);
        when(questionMediaExtractor.extractMedia(persistedQuestionDto))
                .thenReturn(persistedMediaDtos);

        // when
        quizMediaService.deleteMediaOnDeleted(quiz);

        // then
        verify(storageExecutorService).deleteFile("0");
        verify(storageExecutorService).deleteFile("1");
        verify(storageExecutorService).deleteFile("2");
    }

    private List<MediaDto> createMediaDtos(List<String> ids) {
        return ids.stream()
                .map(id -> {
                    MediaDto mediaDto = mock(MediaDto.class);
                    when(mediaDto.getId()).thenReturn(id);
                    return mediaDto;
                })
                .toList();
    }
}