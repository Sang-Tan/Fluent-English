package com.fluentenglish.web.spacedrepetition.word;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WordMemoRepository extends JpaRepository<WordMemo, WordMemoId> {
}
