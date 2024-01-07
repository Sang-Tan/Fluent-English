package com.fluentenglish.web.learningpath.admin.detail;

import com.fluentenglish.web.learningmaterial.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LearningPathDetailRepository extends JpaRepository<LearningPathDetail, LearningPathDetailId> {
    @Query("SELECT t FROM Topic t WHERE t.id IN (SELECT lpd.topic.id FROM LearningPathDetail lpd WHERE lpd.learningPath.id = :#{#learningPathId})")
    List<Topic> findTopicByLearningPathId(Integer learningPathId);

    List<LearningPathDetail> findAllByLearningPathId(Integer learningPathId);
}
