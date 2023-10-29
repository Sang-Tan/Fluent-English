package com.fluentenglish.web.learningmaterial.topic.admin;

import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicSearchDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;

import java.util.List;
import java.util.Map;

public interface TopicService {
    void createTopic(TopicCreateUpdateDto topicDto);

    TopicDto getTopicById(int id);

    List<TopicDto> getAllTopics();

    List<TopicDto> searchTopics(TopicSearchDto topicSearchDto);

    void updateTopic(int id, TopicCreateUpdateDto topicDto);

    void changeLessonPosition(Map<Integer, Integer> newPositionMap);

    void setTopicPublicity(int id, boolean isPublic);

    void deleteTopic(int id);
}
