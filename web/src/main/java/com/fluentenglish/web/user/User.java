package com.fluentenglish.web.user;

import com.fluentenglish.web.gaming.chapter.Chapter;
import com.fluentenglish.web.gaming.user.level.UserLevel;
import com.fluentenglish.web.learning.material.word.Word;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Chapter chapter = new Chapter(1);

    // Progress in the current chapter, ranging from 0.0 (0%) to 1.0 (100%)
    private Float chapterProgress = 0.0f;

    // The current health points percentage of the user, ranging from 0.0 (0%) to 1.0 (100%)
    @Column(name = "current_hp")
    private Float currentHpPercent = 1.0f;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level", referencedColumnName = "level")
    private UserLevel level = new UserLevel(1);

    private Integer experience = 0;

    @Column(name = "enabled")
    private boolean enabled = false;

    public User(Integer id) {
        this.id = id;
    }

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "users__ignored_words",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private Set<Word> ignoredWords;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }
}
