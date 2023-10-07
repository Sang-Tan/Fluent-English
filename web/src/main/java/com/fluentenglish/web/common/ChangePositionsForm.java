package com.fluentenglish.web.common;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChangePositionsForm {
    private Map<Integer, Integer> idPositionMap;
}