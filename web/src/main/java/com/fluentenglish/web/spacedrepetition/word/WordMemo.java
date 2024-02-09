package com.fluentenglish.web.spacedrepetition.word;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "word_memos")
public class WordMemo {
    @EmbeddedId
    private WordMemoId id;

    private Float stability;

    private Float difficulty;

    private Date lastStudy;

    private Date nextStudy;

    public WordMemo(WordMemoId id){
        this.id = id;
    }
}
