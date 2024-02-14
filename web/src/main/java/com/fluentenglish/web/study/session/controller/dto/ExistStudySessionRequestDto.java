package com.fluentenglish.web.study.session.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistStudySessionRequestDto {
    @NotBlank
    private String sessionId;
}
