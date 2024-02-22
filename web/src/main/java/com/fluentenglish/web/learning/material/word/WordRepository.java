package com.fluentenglish.web.learning.material.word;

import com.fluentenglish.web.learning.material.word.dto.WordSearchDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer>, JpaSpecificationExecutor<Word> {
    @Query("SELECT w FROM Word w WHERE lower(w.text) LIKE lower(concat('%', :#{#wordSearchDto.text}, '%')) ORDER BY w.text ASC")
    List<Word> getWords(WordSearchDto wordSearchDto);

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END FROM Word w WHERE w.text = :text")
    boolean existsByText(String text);

    @Query("SELECT w FROM Word w JOIN w.lessons l WHERE l.id = :lessonId ORDER BY w.text ASC")
    List<Word> findAllByLessonId(Integer lessonId);

    @Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END FROM Word w JOIN w.lessons l WHERE w.text = :text AND l.id = :lessonId")
    boolean existsByWordAndLessonId(String text, Integer lessonId);
}
