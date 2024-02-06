package com.fluentenglish.web.spacedrepetition.word;

import com.fluentenglish.web.learning.material.word.Word;
import com.fluentenglish.web.user.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WordMemoId implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private Word word;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordMemoId that = (WordMemoId) o;
        return Objects.equals(user.getId(), that.user.getId()) &&
                Objects.equals(word.getId(), that.word.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), word.getId());
    }
}
