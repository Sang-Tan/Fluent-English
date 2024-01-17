package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.LessonRepository;
import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.LearningPathRepository;
import com.fluentenglish.web.learningpath.admin.detail.LearningPathDetail;
import com.fluentenglish.web.learningpath.admin.detail.LearningPathDetailRepository;
import com.fluentenglish.web.learningpath.admin.request.LearningPathCreateDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class LearningPathServiceImpl implements LearningPathService{
    private final LearningPathRepository learningPathRepository;
    private final LearningPathMapper learningPathMapper;
    private final LearningPathDetailRepository learningPathDetailRepository;
    private final LessonRepository lessonRepository;
    public LearningPathServiceImpl(LearningPathRepository learningPathRepository,
                                   LearningPathMapper learningPathMapper,
                                   LearningPathDetailRepository learningPathDetailRepository,
                                   LessonRepository lessonRepository) {
        this.learningPathRepository = learningPathRepository;
        this.learningPathMapper = learningPathMapper;
        this.learningPathDetailRepository = learningPathDetailRepository;
        this.lessonRepository = lessonRepository;
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
        if (learningPathRepository.existsByName(form.getName())) {
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
    @Transactional
    public void setLessonsByLearningPathId(Integer learningPathId, List<Integer> lessonIds) {
        List<LearningPathDetail> learningPathDetails = learningPathDetailRepository.findAllByLearningPathId(learningPathId);
        learningPathDetailRepository.deleteAll(learningPathDetails);

        LearningPath lpTmp = learningPathRepository.findById(learningPathId).orElseThrow(
                () -> new NotFoundException(String.format("LearningPath with id %d not found", learningPathId))
        );

        learningPathDetailRepository.saveAll(
                lessonRepository.findAllByIdIn(lessonIds).stream()
                        .map(lesson -> {
                            LearningPathDetail learningPathDetail = new LearningPathDetail();
                            learningPathDetail.setLearningPath(lpTmp);
                            learningPathDetail.setLesson(lesson);
                            return learningPathDetail;
                        }).toList()
        );
    }
}