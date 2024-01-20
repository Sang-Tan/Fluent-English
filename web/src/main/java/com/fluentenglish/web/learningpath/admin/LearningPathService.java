package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.learningmaterial.lesson.Lesson;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonSearchDto;
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
    void addLesson(int learningPathId,int lessonIds);
    void removeLesson(int learningPathId,int lessonIds);
    PageDto getUnassignedLessons(Integer learningPathId, int page);

    PageDto searchUnassignedLessons(LessonSearchDto lessonSearchDto, Integer learningPathId, Integer page);
}
