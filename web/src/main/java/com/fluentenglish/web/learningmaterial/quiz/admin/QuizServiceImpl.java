package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.learningmaterial.exercise.ExerciseRepository;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.quiz.QuizRepository;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.AnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.MultipleChoiceAnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice.AnswerChoices;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice.MediaChoices;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.question.QuestionDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.response.QuizDto;
import com.fluentenglish.web.upload.cloud.StorageService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    private final ExerciseRepository exerciseRepository;

    private final QuizRepository quizRepository;

    private final QuizMapper quizMapper;

    private final StorageService storageService;

    private final TaskExecutor taskExecutor;

    public QuizServiceImpl(ExerciseRepository exerciseRepository, QuizRepository quizRepository,
                           QuizMapper quizMapper, StorageService storageService,
                           TaskExecutor taskExecutor) {
        this.exerciseRepository = exerciseRepository;
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
        this.storageService = storageService;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public List<QuizDto> getQuizzesByExerciseId(int exerciseId) {
        if (!exerciseRepository.existsById(exerciseId)) {
            throw new NotFoundException("Exercise not found");
        }

        return quizRepository.findAllByExerciseId(exerciseId)
                .stream()
                .map(quizMapper::toQuizDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizDto getQuizById(int id) {
        Quiz quiz = quizRepository.findById((long) id).orElseThrow(() -> new NotFoundException("Quiz not found"));

        return quizMapper.toQuizDto(quiz);
    }

    @Override
    public int createQuiz(QuizCreateDto quizCreateDto) {
        int exerciseId = quizCreateDto.getExerciseId();
        if (!exerciseRepository.existsById(exerciseId)) {
            throw new UserInputException("The exercise this quiz belongs to does not exist");
        }

        Quiz quiz = quizMapper.toQuiz(quizCreateDto);
        int maxPos = quizRepository.getMaxPositionOfQuizzesInExercise(exerciseId).orElse(0);
        quiz.setPosition(maxPos + 1);

        int quizId = quizRepository.save(quiz).getId();
        makeMediaFilesPermanent(quizCreateDto.getQuestion(), quizCreateDto.getAnswer());

        return quizId;
    }

    @Override
    public void updateQuiz(int id, QuizUpdateDto quizUpdateDto) {
        Quiz quiz = quizRepository.findById((long) id).orElseThrow(() ->
                new UserInputException("The quiz you are trying to update does not exist"));

        quizMapper.updateQuiz(quizUpdateDto, quiz);
        quizRepository.save(quiz);
        makeMediaFilesPermanent(quizUpdateDto.getQuestion(), quizUpdateDto.getAnswer());
    }

    @Override
    public void deleteQuiz(int id) {
        quizRepository.deleteById((long) id);
    }

    private void makeMediaFilesPermanent(QuestionDto question, AnswerDto answer) {
        if (question.getAttachment() != null){
            makeFilePermanent(question.getAttachment().getId());
        }

        if (answer instanceof MultipleChoiceAnswerDto multipleChoiceAnswer) {
            AnswerChoices<?> choices = multipleChoiceAnswer.getChoices();
            if (choices instanceof MediaChoices) {
                ((MediaChoices<?>) choices).getData()
                        .forEach(m -> makeFilePermanent(m.getId()));
            }
        }
    }

    private void makeFilePermanent(String fileId) {
        taskExecutor.execute(() -> storageService.makeFilePermanent(fileId));
    }
}
