package com.fluentenglish.web.gaming.user;

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
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "chapter_num",referencedColumnName = "id")
    private Chapter chapter;
    @ManyToOne
    @JoinColumn(name = "level", referencedColumnName = "id")
    private UserLevel level;
    private Integer experience;
    private Integer chapterProgress;
    private Integer currentHp;
    private Integer email;
    private Integer password;
    private Integer name;
}
