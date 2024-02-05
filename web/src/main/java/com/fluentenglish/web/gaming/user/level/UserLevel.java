package com.fluentenglish.web.gaming.user.level;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_levels")
public class UserLevel {
    @Id
    private Integer level;

    private Integer expToNextLevel;

    private Integer baseHp;

    private Integer baseShield;
}
