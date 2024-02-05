package com.fluentenglish.web.gaming.chapter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    private Integer number;

    private String storyStartEng;

    private String storyStartVi;

    private String storyEndEng;

    private String storyEndVi;
}
