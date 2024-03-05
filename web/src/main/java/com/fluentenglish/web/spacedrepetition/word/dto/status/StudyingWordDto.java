package com.fluentenglish.web.spacedrepetition.word.dto.status;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudyingWordDto extends WordStudyStatusDto {
    private Date lastStudy;
    private Date nextStudy;
    private float proficiency;

    public void setProficiency(float proficiency) {
        if (proficiency < 0.0f || proficiency > 1.0f) {
            throw new IllegalArgumentException("Proficiency should be between 0 and 1");
        }

        this.proficiency = proficiency;
    }
}
