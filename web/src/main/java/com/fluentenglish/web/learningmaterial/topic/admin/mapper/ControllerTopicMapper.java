package com.fluentenglish.web.learningmaterial.topic.admin.mapper;

import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ControllerTopicMapper {
    void updateTopicDtoFromCreateUpdateDto(TopicCreateUpdateDto topicCreateUpdateDto, @MappingTarget TopicDto topicDto);
}
