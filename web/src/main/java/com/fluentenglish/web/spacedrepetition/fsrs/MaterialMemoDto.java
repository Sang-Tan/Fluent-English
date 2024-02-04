package com.fluentenglish.web.spacedrepetition.fsrs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MaterialMemoDto {
    private Float stability;

    private Float difficulty;

    private Date lastStudied;

    private Date nextStudy;
}
