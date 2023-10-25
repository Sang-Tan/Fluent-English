package com.fluentenglish.web.learningmaterial.topic.admin;

import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.learningmaterial.lesson.LessonRepository;
import com.fluentenglish.web.learningmaterial.topic.Topic;
import com.fluentenglish.web.learningmaterial.topic.TopicRepository;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicSearchDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    private final LessonRepository lessonRepository;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper, LessonRepository lessonRepository) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.lessonRepository = lessonRepository;
    }

    public void createTopic(TopicCreateUpdateDto topicDto) {
        if (topicRepository.existsByName(topicDto.getName())) {
            InputErrorInfo inputErrorInfo =
                    new InputErrorInfo("name",
                            "Topic with this name already exists");
            throw new UserInputException(List.of(inputErrorInfo));
        }

        Topic topic = topicMapper.topicCreateUpdateDtoToTopic(topicDto);
        topicRepository.save(topic);
    }

    public TopicDto getTopicById(int id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));

        return topicMapper.topicToTopicDto(topic);
    }

    public List<TopicDto> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map(topicMapper::topicToTopicDto).toList();
    }

    public List<TopicDto> searchTopics(TopicSearchDto topicSearchDto) {
        List<Topic> topics = topicRepository.searchTopics(topicSearchDto);
        return topics.stream().map(topicMapper::topicToTopicDto).toList();
    }

    public void updateTopic(int id, TopicCreateUpdateDto topicDto) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        topicMapper.updateTopicFromDto(topicDto, topic);
        topicRepository.save(topic);
    }

    @Transactional
    public void changeLessonPosition(Map<Integer, Integer> newPositionMap){
        newPositionMap.forEach(lessonRepository::updatePosition);
    }

    public void setTopicPublicity(int id, boolean isPublic) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        topic.setPublic(isPublic);
        topicRepository.save(topic);
    }

    public void deleteTopic(int id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        topicRepository.delete(topic);
    }
}
