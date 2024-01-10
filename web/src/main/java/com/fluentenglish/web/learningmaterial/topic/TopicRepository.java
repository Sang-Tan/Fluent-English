package com.fluentenglish.web.learningmaterial.topic;

import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicSearchDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Query("SELECT t FROM Topic t WHERE lower(t.name) LIKE lower(concat('%', :#{#topicSearchDto.name}, '%'))")
    List<Topic> searchTopics(TopicSearchDto topicSearchDto);

    List<Topic> findAllByIdIn(List<Integer> ids);

    boolean existsByName(String name);
}
