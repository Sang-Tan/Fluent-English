package com.fluentenglish.web.user;

import com.fluentenglish.web.gaming.chapter.Chapter;
import com.fluentenglish.web.gaming.user.level.UserLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

    private Float chapterProgress = 0.0f;

    private Float currentHp = 1.0f;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level", referencedColumnName = "level")
    private UserLevel level;

    private Integer experience = 0;
}
