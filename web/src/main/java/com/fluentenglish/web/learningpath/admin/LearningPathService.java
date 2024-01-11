package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.admin.detail.LearningPathDetail;
import com.fluentenglish.web.learningpath.admin.request.LearningPathCreateDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathUpdateDto;

import java.util.List;

public interface LearningPathService {
    LearningPath createLearningPath(LearningPathCreateDto form);
    List<LearningPath> getAllLearningPath();
    LearningPath getLearningPath(Integer learningPathId);
    LearningPath updateLearningPath(Integer learningPathId, LearningPathUpdateDto form);
    void deleteLearningPath(Integer learningPathId);
    LearningPath setLearningPathPublicity(Integer learningPathId, boolean isPublic);
    List<Lesson> getLessonsByLearningPathId(Integer learningPathId);
    List<LearningPathDetail> setLessonsByLearningPathId(Integer learningPathId, List<Integer> lessonIds);
}
