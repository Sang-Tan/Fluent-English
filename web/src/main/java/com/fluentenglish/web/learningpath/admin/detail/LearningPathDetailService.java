package com.fluentenglish.web.learningpath.admin.detail;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.learningmaterial.topic.Topic;
import com.fluentenglish.web.learningmaterial.topic.TopicRepository;
import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.LearningPathRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LearningPathDetailService {
    private final LearningPathDetailRepository learningPathDetailRepository;
    private final LearningPathRepository learningPathRepository;
    private final TopicRepository topicRepository;
    public LearningPathDetailService(LearningPathDetailRepository learningPathDetailRepository,
                                     LearningPathRepository learningPathRepository,
                                     TopicRepository topicRepository) {
        this.learningPathDetailRepository = learningPathDetailRepository;
        this.learningPathRepository = learningPathRepository;
        this.topicRepository = topicRepository;
    }

    public List<Topic> getTopicsByLearningPathId(Integer learningPathId) {
        return learningPathDetailRepository.findTopicByLearningPathId(learningPathId);
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
