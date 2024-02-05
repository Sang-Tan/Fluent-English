package com.fluentenglish.web.gaming.enemy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "enemies")
public class Enemy {
    @Id
    private Integer id;

    private String name;
}
