package com.fluentenglish.web.user;

import com.fluentenglish.web.gaming.chapter.Chapter;
import com.fluentenglish.web.gaming.user.level.UserLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_num", referencedColumnName = "number")
    private Chapter chapter;

    // Progress in the current chapter, ranging from 0.0 (0%) to 1.0 (100%)
    private Float chapterProgress = 0.0f;

    // The current health points percentage of the user, ranging from 0.0 (0%) to 1.0 (100%)
    @Column(name = "current_hp")
    private Float currentHpPercent = 1.0f;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level", referencedColumnName = "level")
    private UserLevel level;

    private Integer experience = 0;

    public User(Integer id) {
        this.id = id;
    }
}
