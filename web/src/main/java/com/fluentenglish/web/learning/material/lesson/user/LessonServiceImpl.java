package com.fluentenglish.web.learning.material.lesson.user;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.mapper.PageMapper;
import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learning.material.lesson.Lesson;
import com.fluentenglish.web.learning.material.lesson.LessonRepository;
import com.fluentenglish.web.learning.material.lesson.ServiceLessonMapper;
import com.fluentenglish.web.learning.material.lesson.dto.LessonDto;
import com.fluentenglish.web.learning.material.lesson.dto.LessonSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("userLessonService")
public class LessonServiceImpl implements LessonService {
    private final static int PAGE_SIZE = 10;
    private final LessonRepository lessonRepository;
    private final ServiceLessonMapper lessonMapper;
    private final PageMapper pageMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, ServiceLessonMapper lessonMapper, PageMapper pageMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.pageMapper = pageMapper;
    }

    public LessonDto getLessonById(int id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Lesson not found"));
        return lessonMapper.lessonToLessonDto(lesson);
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
}
