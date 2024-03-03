package com.fluentenglish.web.gaming.user.level;

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
@Table(name = "user_levels")
public class UserLevel {
    @Id
    private Integer level;

    private Integer expToNextLevel;

    private Integer baseHp;

    private Integer baseShield;

    public UserLevel(int level) {
        this.level = level;
    }
}
