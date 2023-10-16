package com.fluentenglish.web.common.viewattribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class UserInfo {
    private String username;
}
