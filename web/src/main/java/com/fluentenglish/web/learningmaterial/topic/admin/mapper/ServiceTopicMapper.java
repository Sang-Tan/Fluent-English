package com.fluentenglish.web.learningmaterial.topic.admin.mapper;

import com.fluentenglish.web.learningmaterial.topic.Topic;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceTopicMapper {

    Topic topicCreateUpdateDtoToTopic(TopicCreateUpdateDto topicCreateUpdateDto);

    TopicDto topicToTopicDto(Topic topic);

    void updateTopicFromDto(TopicCreateUpdateDto topicCreateUpdateDto, @MappingTarget Topic topic);
}
