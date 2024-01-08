package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.LearningPathRepository;
import com.fluentenglish.web.learningpath.admin.request.LearningPathCreateDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class LearningPathService {
    private final LearningPathRepository learningPathRepository;
    private final LearningPathMapper learningPathMapper;
    public LearningPathService(LearningPathRepository learningPathRepository, LearningPathMapper learningPathMapper) {
        this.learningPathRepository = learningPathRepository;
        this.learningPathMapper = learningPathMapper;
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
    public List<LearningPath> getAllLearningPath() {
        return learningPathRepository.findAll();
    }

    public LearningPath getLearningPath(Integer learningPathId) {
        return learningPathRepository.findById(learningPathId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Learning path id: %d not found", learningPathId)));
    }

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

    public void deleteLearningPath(Integer learningPathId) {
        if(learningPathRepository.existsById(learningPathId))
            throw new NotFoundException(String.format("Learning path id: %d not found", learningPathId));

        learningPathRepository.deleteById(learningPathId);
    }
    public LearningPath setLearningPathPublicity(Integer learningPathId, boolean isPublic) {
        LearningPath learningPath = learningPathRepository.findById(learningPathId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Learning path id: %d not found", learningPathId)));

        learningPath.setPublic(isPublic);
        return learningPathRepository.save(learningPath);
    }


}