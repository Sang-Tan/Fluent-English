package com.fluentenglish.web.learning.material.word;

import com.fluentenglish.web.common.mapper.FileResourceDtoMapper;
import com.fluentenglish.web.common.mapper.JacksonMapper;
import com.fluentenglish.web.learning.material.lesson.Lesson;
import com.fluentenglish.web.learning.material.word.dto.WordCreateUpdateDto;
import com.fluentenglish.web.learning.material.word.dto.WordDetailDto;
import com.fluentenglish.web.learning.material.word.dto.WordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {FileResourceDtoMapper.class, JacksonMapper.class})
public interface ServiceWordMapper {
    WordDto toWordDto(Word word);

    @Mapping(target = "lessonIds", expression = "java(toLessonIds(word.getLessons()))")
    WordDetailDto toWordDetailDto(Word word);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    Word toWord(WordCreateUpdateDto wordCreateUpdateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    void updateWord(WordCreateUpdateDto wordCreateUpdateDto, @MappingTarget Word word);

    default Set<Integer> toLessonIds(Set<Lesson> lessons) {
        if (lessons == null) {
            return null;
        }
        return lessons.stream()
                .map(Lesson::getId)
                .collect(Collectors.toSet());
    }

    default Set<Lesson> fromLessonIds(Set<Integer> lessonIds) {
        if (lessonIds == null) {
            return null;
        }
        return lessonIds.stream()
                .map(id -> Lesson.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}
