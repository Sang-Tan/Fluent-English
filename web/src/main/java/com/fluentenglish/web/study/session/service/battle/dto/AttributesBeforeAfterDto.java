package com.fluentenglish.web.study.session.service.battle.dto;

import com.fluentenglish.web.gaming.user.dto.UserAttributesDto;
import lombok.*;

@Getter
@Setter
public class AttributesBeforeAfterDto {
    private UserAttributesDto before;
    private UserAttributesDto after;
}
