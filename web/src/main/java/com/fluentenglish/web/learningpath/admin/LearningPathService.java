package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.learningmaterial.topic.Topic;
import com.fluentenglish.web.learningmaterial.topic.TopicRepository;
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
public class LearningPathService {
    private final LearningPathRepository learningPathRepository;
    private final LearningPathMapper learningPathMapper;
    private final LearningPathDetailRepository learningPathDetailRepository;
    private final TopicRepository topicRepository;
    public LearningPathService(LearningPathRepository learningPathRepository,
                               LearningPathMapper learningPathMapper,
                               LearningPathDetailRepository learningPathDetailRepository,
                               TopicRepository topicRepository) {
        this.learningPathRepository = learningPathRepository;
        this.learningPathMapper = learningPathMapper;
        this.learningPathDetailRepository = learningPathDetailRepository;
        this.topicRepository = topicRepository;
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
        learningPathRepository.deleteById(learningPathId);
    }
    public LearningPath setLearningPathPublicity(Integer learningPathId, boolean isPublic) {
        LearningPath learningPath = learningPathRepository.findById(learningPathId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Learning path id: %d not found", learningPathId)));

        learningPath.setPublic(isPublic);
        return learningPathRepository.save(learningPath);
    }

    public List<Topic> getTopicsByLearningPathId(Integer learningPathId) {
        return learningPathDetailRepository.findTopicsByLearningPathId(learningPathId);
    }

    @Transactional
    public List<LearningPathDetail> setTopicsByLearningPathId(Integer learningPathId, List<Integer> topicIds) {
        List<LearningPathDetail> learningPathDetails = learningPathDetailRepository.findAllByLearningPathId(learningPathId);
        learningPathDetailRepository.deleteAll(learningPathDetails);

        LearningPath lpTmp = learningPathRepository.findById(learningPathId).orElseThrow(
                () -> new NotFoundException(String.format("LearningPath with id %d not found", learningPathId))
        );

        return learningPathDetailRepository.saveAll(
                topicRepository.findAllByIdIn(topicIds).stream()
                        .map(topic -> {
                            LearningPathDetail learningPathDetail = new LearningPathDetail();
                            learningPathDetail.setLearningPath(lpTmp);
                            learningPathDetail.setTopic(topic);
                            return learningPathDetail;
                        }).toList()
        );
    }
}