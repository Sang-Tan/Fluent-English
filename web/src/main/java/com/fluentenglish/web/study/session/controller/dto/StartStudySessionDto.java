package com.fluentenglish.web.study.session.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StartStudySessionDto {
    private List<Integer> wordIds;
}
