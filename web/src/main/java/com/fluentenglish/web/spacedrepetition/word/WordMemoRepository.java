package com.fluentenglish.web.spacedrepetition.word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordMemoRepository extends JpaRepository<WordMemo, WordMemoId>,
        JpaSpecificationExecutor<WordMemo> {
    @Query("SELECT wm FROM WordMemo wm WHERE wm.id.user.id = :userId AND wm.id.word.id IN :wordIds")
    List<WordMemo> findAllByUserIdAndWordIds(int userId, List<Integer> wordIds);
}
