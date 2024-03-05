package com.fluentenglish.web.spacedrepetition.word.mapper;

import com.fluentenglish.web.spacedrepetition.fsrs.MaterialMemoDto;
import com.fluentenglish.web.spacedrepetition.word.WordMemo;
import com.fluentenglish.web.spacedrepetition.word.dto.status.StudyingWordDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
@DecoratedWith(WordMemoMapperDecorator.class)
public interface WordMemoMapper {
    @Mapping(target = "id", ignore = true)
    WordMemo toEntity(MaterialMemoDto materialMemoDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(MaterialMemoDto materialMemoDto, @MappingTarget WordMemo wordMemo);

    @Mapping(target = "wordId", source = "id.word.id")
    StudyingWordDto toStudyingWordDto(WordMemo wordMemo);
}
