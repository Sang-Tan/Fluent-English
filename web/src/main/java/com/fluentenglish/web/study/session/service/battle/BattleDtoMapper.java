package com.fluentenglish.web.study.session.service.battle;

import com.fluentenglish.web.study.session.dao.battle.BattleInfo;
import com.fluentenglish.web.study.session.service.battle.dto.BattleInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BattleDtoMapper {
    BattleInfoDto toDto(BattleInfo battleInfo);
}
