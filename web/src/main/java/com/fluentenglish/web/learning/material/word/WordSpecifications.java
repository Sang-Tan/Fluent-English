package com.fluentenglish.web.learning.material.word;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class WordSpecifications {
    public Specification<Word> textContains(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("text"), "%" + text + "%");
    }

    public Specification<Word> wordIdsIn(Iterable<Integer> wordIds) {
        return (root, query, criteriaBuilder) -> root.get("id").in(wordIds);
    }
}
