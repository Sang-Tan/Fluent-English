package com.fluentenglish.web.gaming.chapter.enemy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChapterEnemyRepository extends JpaRepository<ChapterEnemy, ChapterEnemyId> {
    @Query(value = "SELECT * FROM chapters__enemies ce WHERE ce.chapter_num = ?1 ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<ChapterEnemy> findRandomByChapterNumber(Integer chapterNumber);
}
