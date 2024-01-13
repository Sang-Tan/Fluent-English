package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fluentenglish.web.common.mapper.JacksonMapper;
import com.fluentenglish.web.learningmaterial.quiz.Quiz;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.AnswerDtoMapper;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.question.QuestionDtoMapper;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.response.QuizDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses ={QuestionDtoMapper.class, AnswerDtoMapper.class, JacksonMapper.class})
public interface QuizMapper {
    @Mapping(target = "exerciseId", source = "exercise.id")
    QuizDto toQuizDto(Quiz quiz);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "exercise.id", source = "exerciseId")
    Quiz toQuiz(QuizCreateDto quizCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "exercise", ignore = true)
    void updateQuiz(QuizUpdateDto quizUpdateDto, @MappingTarget Quiz quiz);
}
