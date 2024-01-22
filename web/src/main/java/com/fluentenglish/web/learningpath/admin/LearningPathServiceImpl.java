package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.common.mapper.PageMapper;
import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.LessonRepository;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.LearningPathRepository;
import com.fluentenglish.web.learningpath.admin.detail.LearningPathDetail;
import com.fluentenglish.web.learningpath.admin.detail.LearningPathDetailId;
import com.fluentenglish.web.learningpath.admin.detail.LearningPathDetailRepository;
import com.fluentenglish.web.learningpath.admin.request.LearningPathCreateDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LearningPathServiceImpl implements LearningPathService{
    private final static int PAGE_SIZE = 10;
    private final LearningPathRepository learningPathRepository;
    private final LearningPathMapper learningPathMapper;
    private final LearningPathDetailRepository learningPathDetailRepository;
    private final LessonRepository lessonRepository;
    private final PageMapper pageMapper;
    public LearningPathServiceImpl(LearningPathRepository learningPathRepository,
                                   LearningPathMapper learningPathMapper,
                                   LearningPathDetailRepository learningPathDetailRepository,
                                   LessonRepository lessonRepository,
                                   PageMapper pageMapper) {
        this.learningPathRepository = learningPathRepository;
        this.learningPathMapper = learningPathMapper;
        this.learningPathDetailRepository = learningPathDetailRepository;
        this.lessonRepository = lessonRepository;
        this.pageMapper = pageMapper;
    }

    public LearningPath createLearningPath(LearningPathCreateDto form) {
        if (learningPathRepository.existsByName(form.getName())) {
            InputErrorInfo inputErrorInfo =
                    new InputErrorInfo("name",
                            "Learning path with this name already exists");
            throw new UserInputException(List.of(inputErrorInfo));
        }
        LearningPath learningPath = learningPathMapper.toLearningPath(form);

        return learningPathRepository.save(learningPath);
    }

    @Override
    public List<LearningPath> getAllLearningPath() {
        return learningPathRepository.findAll();
    }

    @Override
    public LearningPath getLearningPath(Integer learningPathId) {
        return learningPathRepository.findById(learningPathId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Learning path id: %d not found", learningPathId)));
    }

    @Override
    public LearningPath updateLearningPath(Integer learningPathId, LearningPathUpdateDto form) {
        if (learningPathRepository.existsByNameExcludeId(form.getName(), learningPathId)) {
            InputErrorInfo inputErrorInfo =
                    new InputErrorInfo("name",
                            "Learning path with this name already exists");
            throw new UserInputException(List.of(inputErrorInfo));
        }

        LearningPath learningPath = learningPathRepository.findById(learningPathId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Learning path id: %d not found", learningPathId)));

        learningPathMapper.updateLearningPath(form, learningPath);

        return learningPathRepository.save(learningPath);
    }

    @Override
    public void deleteLearningPath(Integer learningPathId) {
        learningPathRepository.deleteById(learningPathId);
    }

    @Override
    public LearningPath setLearningPathPublicity(Integer learningPathId, boolean isPublic) {
        LearningPath learningPath = learningPathRepository.findById(learningPathId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Learning path id: %d not found", learningPathId)));

        learningPath.setPublic(isPublic);
        return learningPathRepository.save(learningPath);
    }

    @Override
    public List<Lesson> getLessonsByLearningPathId(Integer learningPathId) {
        return learningPathDetailRepository.findLessonsByLearningPathId(learningPathId);
    }
    @Override
    public void addLesson(int learningPathId,int lessonId){
        LearningPathDetail learningPathDetail = new LearningPathDetail();
        LearningPath learningPath = learningPathRepository.findById(learningPathId).orElseThrow(
                () -> new NotFoundException(String.format("LearningPath with id %d not found", learningPathId))
        );
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new NotFoundException(String.format("Lesson with id %d not found", lessonId))
        );
        learningPathDetail.setId(new LearningPathDetailId(learningPathId,lessonId));
        learningPathDetail.setLearningPath(learningPath);
        learningPathDetail.setLesson(lesson);
        Integer maxPosition = learningPathDetailRepository.findMaxPositionByLearningPathId(learningPathId);
        if(maxPosition == null){
            maxPosition = 0;
        }
        learningPathDetail.setPosition(maxPosition+1);
        learningPathDetailRepository.save(learningPathDetail);
    }
    @Override
    public void removeLesson(int learningPathId,int lessonId){
        LearningPathDetail lpd = learningPathDetailRepository.findByLearningPathIdAndLessonId(learningPathId,lessonId);
        learningPathDetailRepository.delete(lpd);
    }

    @Override
    public PageDto getUnassignedLessons(Integer learningPathId, int page) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("name"));
        return pageMapper.toPageDto(learningPathDetailRepository.findUnassignedLesson(learningPathId, pageable));
    }

    @Override
    public PageDto searchUnassignedLessons(LessonSearchDto lessonSearchDto, Integer learningPathId, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("name"));
        return pageMapper.toPageDto(learningPathDetailRepository.searchUnassignedLesson(lessonSearchDto, learningPathId, pageable));
    }
}