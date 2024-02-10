package com.fluentenglish.web.gaming.chapter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
}
