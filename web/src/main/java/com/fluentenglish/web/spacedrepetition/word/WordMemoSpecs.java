package com.fluentenglish.web.spacedrepetition.word;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WordMemoSpecs {
    public Specification<WordMemo> userIdEquals(int userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id").get("user").get("id"), userId);
    }

    public Specification<WordMemo> nextStudyAtLeast(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("nextStudy"), date);
    }
}
