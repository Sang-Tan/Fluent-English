package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.LessonRepository;
import com.fluentenglish.web.learningmaterial.lesson.admin.mapper.ServiceLessonMapper;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import com.fluentenglish.web.learningmaterial.lesson.introduction.Introduction;
import com.fluentenglish.web.learningmaterial.lesson.introduction.IntroductionRepository;
import com.fluentenglish.web.learningmaterial.topic.Topic;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final IntroductionRepository introductionRepository;

    private final ServiceLessonMapper lessonMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, IntroductionRepository introductionRepository, ServiceLessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.introductionRepository = introductionRepository;
        this.lessonMapper = lessonMapper;
    }

    @Override
    public void createLesson(LessonCreateDto lessonCreateDto) {
        Lesson lesson = lessonMapper.toLesson(lessonCreateDto);

        lesson.setTopic(Topic.builder().id(lessonCreateDto.getTopicId()).build());

        int maxPosition = lessonRepository
                .getMaxPositionOfLessonsInTopic(lessonCreateDto.getTopicId())
                .orElse(0);
        lesson.setPosition(maxPosition + 1);

        lessonRepository.save(lesson);
    }

    @Override
    public LessonDto getLessonById(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));

        return lessonMapper.toLessonDto(lesson);
    }

    @Override
    public int getTopicIdOfLesson(int lessonId) {
        if(!lessonRepository.existsById(lessonId)) {
            throw new NotFoundException("Lesson not found");
        }
        return lessonRepository.getTopicIdOfLesson(lessonId);
    }

    @Override
    public void updateLesson(int lessonId, LessonUpdateDto lessonUpdateDto) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));
        lessonMapper.updateLesson(lessonUpdateDto, lesson);
        lessonRepository.save(lesson);
    }

    @Override
    public void setLessonPublicity(int lessonId, boolean isPublic) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));
        lesson.setPublic(isPublic);
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(int lessonId) {
        if(!lessonRepository.existsById(lessonId)) {
            throw new NotFoundException("Lesson not found");
        }
        lessonRepository.deleteById(lessonId);
    }

    @Override
    public String getLessonIntroduction(int lessonId) {
        return introductionRepository.findByLessonId(lessonId)
                .map(Introduction::getContent)
                .orElse("");
    }

    @Override
    public void updateLessonIntroduction(int lessonId, String content) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found"));

        Introduction introduction = introductionRepository.findByLessonId(lessonId)
                .orElse(Introduction.builder().lessonId(lessonId).build());

        introduction.setContent(content);

        introductionRepository.save(introduction);
    }
}
