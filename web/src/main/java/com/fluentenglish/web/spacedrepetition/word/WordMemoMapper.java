package com.fluentenglish.web.spacedrepetition.word;

import com.fluentenglish.web.spacedrepetition.fsrs.MaterialMemoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WordMemoMapper {
    @Mapping(target = "id", ignore = true)
    WordMemo toEntity(MaterialMemoDto materialMemoDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(MaterialMemoDto materialMemoDto, @MappingTarget WordMemo wordMemo);
}
