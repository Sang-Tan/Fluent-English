package com.fluentenglish.web.gaming.chapter.enemy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chapters__enemies")
public class ChapterEnemy {

    @EmbeddedId
    private ChapterEnemyId id;

    private Integer damage;

    private Integer expGain;
}
