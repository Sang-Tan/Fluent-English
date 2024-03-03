package com.fluentenglish.web.gaming.chapter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chapters")
public class Chapter {
    @Id
    private Integer number;

    private String storyStartEng;

    private String storyStartVi;

    private String storyEndEng;

    private String storyEndVi;

    public Chapter(int number) {
        this.number = number;
    }
}
