package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.learningmaterial.exercise.ExerciseRepository;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.quiz.QuizRepository;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.response.QuizDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizMediaService quizMediaService;

    private final ExerciseRepository exerciseRepository;

    private final QuizRepository quizRepository;

    private final QuizMapper quizMapper;

    public QuizServiceImpl(QuizMediaService quizMediaService,
                           ExerciseRepository exerciseRepository,
                           QuizRepository quizRepository,
                           QuizMapper quizMapper) {
        this.quizMediaService = quizMediaService;
        this.exerciseRepository = exerciseRepository;
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    @Override
    public List<QuizDto> getQuizzesByExerciseId(Integer exerciseId) {
        if (!exerciseRepository.existsById(exerciseId)) {
            throw new NotFoundException("Exercise not found");
        }

        return quizRepository.findAllByExerciseIdOrderedByPositionAsc(exerciseId)
                .stream()
                .map(quizMapper::toQuizDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizDto getQuizById(Integer id) {
        Quiz quiz = quizRepository.findById((long) id).orElseThrow(() -> new NotFoundException("Quiz not found"));

        return quizMapper.toQuizDto(quiz);
    }

    @Override
    public Integer createQuiz(QuizCreateDto quizCreateDto) {
        Integer exerciseId = quizCreateDto.getExerciseId();
        if (!exerciseRepository.existsById(exerciseId)) {
            throw new UserInputException("The exercise this quiz belongs to does not exist");
        }

        Quiz quiz = quizMapper.toQuiz(quizCreateDto);
        Integer maxPos = quizRepository.getMaxPositionOfQuizzesInExercise(exerciseId).orElse(0);
        quiz.setPosition(maxPos + 1);

        Integer quizId = quizRepository.save(quiz).getId();
        quizMediaService.saveMediaOnCreated(quizCreateDto);

        return quizId;
    }

    @Override
    public void updateQuiz(Integer id, QuizUpdateDto quizUpdateDto) {
        Quiz quiz = quizRepository.findById((long) id).orElseThrow(() ->
                new UserInputException("The quiz you are trying to update does not exist"));

        quizMediaService.saveMediaOnUpdated(quizUpdateDto, quiz);

        quizMapper.updateQuiz(quizUpdateDto, quiz);
        quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(Integer id) {
        if (!quizRepository.existsById((long) id)) {
            return;
        }
        Quiz quiz = quizRepository.getReferenceById((long) id);

        quizRepository.delete(quiz);
        quizMediaService.deleteMediaOnDeleted(quiz);
    }
}
