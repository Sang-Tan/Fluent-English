package com.fluentenglish.web.gaming.chapter.enemy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChapterEnemyRepository extends JpaRepository<ChapterEnemy, ChapterEnemyId> {
    @Query("SELECT ce FROM ChapterEnemy ce WHERE ce.id.chapter.number = ?1 ORDER BY RAND() LIMIT 1")
    public Optional<ChapterEnemy> findRandomByChapterNumber(Integer chapterNumber);
}
