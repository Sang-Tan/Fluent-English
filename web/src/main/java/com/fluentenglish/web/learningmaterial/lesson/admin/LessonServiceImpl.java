package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.common.mapper.PageMapper;
import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learningmaterial.exercise.ExerciseRepository;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.LessonRepository;
import com.fluentenglish.web.learningmaterial.lesson.admin.mapper.ServiceLessonMapper;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LessonServiceImpl implements LessonService {
    private final static int PAGE_SIZE = 10;
    private final LessonRepository lessonRepository;

    private final ServiceLessonMapper lessonMapper;

    private final ExerciseRepository exerciseRepository;
    private final PageMapper pageMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, ServiceLessonMapper lessonMapper, ExerciseRepository exerciseRepository, PageMapper pageMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.exerciseRepository = exerciseRepository;
        this.pageMapper = pageMapper;
    }

    public void createLesson(LessonCreateUpdateDto lessonDto) {
        if (lessonRepository.existsByName(lessonDto.getName())) {
            InputErrorInfo inputErrorInfo =
                    new InputErrorInfo("name",
                            "Lesson with this name already exists");
            throw new UserInputException(List.of(inputErrorInfo));
        }

        Lesson lesson = lessonMapper.lessonCreateUpdateDtoToLesson(lessonDto);
        lessonRepository.save(lesson);
    }

    public LessonDto getLessonById(int id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Lesson not found"));
        return lessonMapper.lessonToLessonDto(lesson);
    }

    public boolean lessonExistsById(int id) {
        return lessonRepository.existsById(id);
    }

    public PageDto getAllLessons(int page) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("name"));
        Page<Lesson> lessons = lessonRepository.findAll(pageable);
        return pageMapper.toPageDto(lessons.map(lessonMapper::lessonToLessonDto));
    }

    public PageDto searchLessons(LessonSearchDto lessonSearchDto, int page) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("name"));
        Page<Lesson> lessons = lessonRepository.searchLessons(lessonSearchDto, pageable);
        return pageMapper.toPageDto(lessons.map(lessonMapper::lessonToLessonDto));
    }

    public void updateLesson(int id, LessonCreateUpdateDto lessonDto) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Lesson not found"));
        if (!lesson.getName().equals(lessonDto.getName()) && lessonRepository.existsByName(lessonDto.getName())) {
            InputErrorInfo inputErrorInfo =
                    new InputErrorInfo("name",
                            "Lesson with this name already exists");
            throw new UserInputException(List.of(inputErrorInfo));
        }

        lessonMapper.updateLessonFromDto(lessonDto, lesson);
        lessonRepository.save(lesson);
    }

    @Transactional
    public void changeExercisePosition(Map<Integer, Integer> newPositionMap){
        newPositionMap.forEach(exerciseRepository::updatePosition);
    }

    public void setLessonPublicity(int id, boolean isPublic) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Lesson not found"));
        lesson.setPublic(isPublic);
        lessonRepository.save(lesson);
    }

    public void deleteLesson(int id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Lesson not found"));
        lessonRepository.delete(lesson);
    }
}
