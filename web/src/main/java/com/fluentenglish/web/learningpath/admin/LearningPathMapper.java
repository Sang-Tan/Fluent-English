package com.fluentenglish.web.learningpath.admin;

import com.fluentenglish.web.learningpath.LearningPath;
import com.fluentenglish.web.learningpath.admin.request.LearningPathCreateDto;
import com.fluentenglish.web.learningpath.admin.request.LearningPathUpdateDto;
import com.fluentenglish.web.learningpath.admin.response.LearningPathDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LearningPathMapper{
    LearningPath toLearningPath(LearningPathCreateDto learningPathCreateDto);
    LearningPathDto ToLearningPathDto(LearningPath learningPath);
    void updateLearningPath(LearningPathUpdateDto learningPathUpdateDto, @MappingTarget LearningPath learningPath);
}
