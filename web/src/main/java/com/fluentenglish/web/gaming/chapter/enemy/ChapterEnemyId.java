package com.fluentenglish.web.gaming.chapter.enemy;

import com.fluentenglish.web.gaming.chapter.Chapter;
import com.fluentenglish.web.gaming.enemy.Enemy;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ChapterEnemyId implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_num", referencedColumnName = "number")
    private Chapter chapter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enemy_id", referencedColumnName = "id")
    private Enemy enemy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChapterEnemyId that = (ChapterEnemyId) o;
        return Objects.equals(chapter.getNumber(), that.chapter.getNumber()) &&
                Objects.equals(enemy.getId(), that.enemy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(chapter.getNumber(), enemy.getId());
    }
}
