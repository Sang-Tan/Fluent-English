package com.fluentenglish.web.learningpath;

import com.fluentenglish.web.learningpath.exception.LearningPathAlreadyExistsException;
import com.fluentenglish.web.learningpath.exception.LearningPathNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LearningPathService {
    private final LearningPathRepository learningPathRepository;
    private final LearningPathMapper learningPathMapper;

    public LearningPath createLearningPath(LearningPathForm form) {
        if(learningPathRepository.findByName(form.getName()).isPresent()){
            throw new LearningPathAlreadyExistsException(form.getName());
        }
        LearningPath learningPath = learningPathMapper.toLearningPath(form);
        return this.learningPathRepository.save(learningPath);
    }
    public List<LearningPath> getAllLearningPath() {
        return this.learningPathRepository.findAll();
    }

    public LearningPath getLearningPath(Integer learningPathId) {
        return this.learningPathRepository.findById(learningPathId).orElseThrow(() -> new LearningPathNotFoundException(learningPathId));
    }

    public LearningPath updateLearningPath(Integer learningPathId, LearningPathForm form) {
        LearningPath learningPath = learningPathMapper.toLearningPath(form);
        learningPath.setId(learningPathId);
        if(learningPathRepository.findById(learningPathId).isEmpty()){
            throw new LearningPathNotFoundException(learningPathId);
        }
        learningPathRepository.findByName(form.getName()).ifPresent(learningPath1 -> {
            if(!learningPath1.getId().equals(learningPathId)){
                throw new LearningPathAlreadyExistsException(form.getName());
            }
        });
        return this.learningPathRepository.save(learningPath);
    }

    public void deleteLearningPath(Integer learningPathId) {
        if(learningPathRepository.findById(learningPathId).isEmpty()){
            throw new LearningPathNotFoundException(learningPathId);
        }
        this.learningPathRepository.deleteById(learningPathId);
    }
    public LearningPath setLearningPathPublicity(Integer learningPathId, boolean isPublic) {
        LearningPath learningPath = learningPathRepository.findById(learningPathId).orElseThrow(() -> new LearningPathNotFoundException(learningPathId));
        learningPath.setPublic(isPublic);
        return this.learningPathRepository.save(learningPath);
    }


}