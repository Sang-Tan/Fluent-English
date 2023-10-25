package com.fluentenglish.web.learningmaterial.topic.admin;

import com.fluentenglish.web.learningmaterial.topic.Topic;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    Topic topicCreateUpdateDtoToTopic(TopicCreateUpdateDto topicCreateUpdateDto);

    void updateTopicFromDto(TopicCreateUpdateDto topicCreateUpdateDto, @MappingTarget Topic topic);

    TopicDto topicToTopicDto(Topic topic);
}
