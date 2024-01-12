package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fluentenglish.web.learningmaterial.exercise.ExerciseRepository;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.quiz.QuizRepository;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @InjectMocks
    QuizServiceImpl quizServiceUnderTest;

    @Mock
    QuizMediaService quizMediaService;

    @Mock
    ExerciseRepository exerciseRepository;

    @Mock
    QuizRepository quizRepository;

    @Mock
    QuizMapper quizMapper;

    @Test
    void createQuiz_shouldSaveAndMakeMediaPermanent() {
        QuizCreateDto quizCreateDto = new QuizCreateDto();
        when(exerciseRepository.existsById(any())).thenReturn(true);
        when(quizRepository.save(any())).thenReturn(new Quiz());
        when(quizRepository.getMaxPositionOfQuizzesInExercise(any())).thenReturn(Optional.of(0));
        when(quizMapper.toQuiz(any())).thenReturn(new Quiz());

        quizServiceUnderTest.createQuiz(quizCreateDto);

        verify(quizRepository).save(any());
        verify(quizMediaService).saveMediaOnCreated(quizCreateDto);
    }


    @Test
    void updateQuiz_shouldSaveAndMakeChangeToMedia(){
        when(quizRepository.findById(any())).thenReturn(Optional.of(new Quiz()));

        quizServiceUnderTest.updateQuiz(0, new QuizUpdateDto());

        verify(quizRepository).save(any());
        verify(quizMediaService).saveMediaOnUpdated(any(), any());
    }

    @Test
    void updateQuiz_shouldSaveMediaBeforeMappingToQuiz(){
        when(quizRepository.findById(any())).thenReturn(Optional.of(new Quiz()));

        quizServiceUnderTest.updateQuiz(0, new QuizUpdateDto());

        InOrder inOrder = inOrder(quizMediaService, quizMapper);
        inOrder.verify(quizMediaService).saveMediaOnUpdated(any(), any());
        inOrder.verify(quizMapper).updateQuiz(any(), any());
    }

    @Test
    void deleteQuiz_shouldDeleteQuizAndMedia(){
        when(quizRepository.existsById(any())).thenReturn(true);

        quizServiceUnderTest.deleteQuiz(0);

        verify(quizRepository).delete(any());
        verify(quizMediaService).deleteMediaOnDeleted(any());
    }
}