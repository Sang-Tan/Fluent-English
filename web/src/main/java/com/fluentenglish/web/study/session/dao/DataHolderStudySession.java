package com.fluentenglish.web.study.session.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fluentenglish.web.study.session.dao.battle.DataHolderBattle;
import com.fluentenglish.web.study.session.dao.battle.DataHolderBattleDeserializer;
import com.fluentenglish.web.study.session.dao.battle.DataHolderBattleSerializer;
import com.fluentenglish.web.study.session.dao.lastinteraction.DataHolderLastInteraction;
import com.fluentenglish.web.study.session.dao.lastinteraction.DataHolderLastInteractionDeserializer;
import com.fluentenglish.web.study.session.dao.lastinteraction.DataHolderLastInteractionSerializer;
import com.fluentenglish.web.study.session.dao.quiz.DataHolderQuizzesQueue;
import com.fluentenglish.web.study.session.dao.quiz.DataHolderQuizzesQueueDeserializer;
import com.fluentenglish.web.study.session.dao.quiz.DataHolderQuizzesQueueSerializer;
import com.fluentenglish.web.study.session.dao.score.DataHolderWordsScoreDeserializer;
import com.fluentenglish.web.study.session.dao.score.DataHolderWordsScores;
import com.fluentenglish.web.study.session.dao.score.DataHolderWordsScoresSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataHolderStudySession implements StudySession {
    private int userId;

    private String id;

    @JsonSerialize(using = DataHolderWordsScoresSerializer.class)
    @JsonDeserialize(using = DataHolderWordsScoreDeserializer.class)
    private DataHolderWordsScores wordsScores = new DataHolderWordsScores();

    @JsonSerialize(using = DataHolderLastInteractionSerializer.class)
    @JsonDeserialize(using = DataHolderLastInteractionDeserializer.class)
    private DataHolderLastInteraction lastInteraction = new DataHolderLastInteraction();

    @JsonSerialize(using = DataHolderBattleSerializer.class)
    @JsonDeserialize(using = DataHolderBattleDeserializer.class)
    private DataHolderBattle battle = new DataHolderBattle();

    @JsonSerialize(using = DataHolderQuizzesQueueSerializer.class)
    @JsonDeserialize(using = DataHolderQuizzesQueueDeserializer.class)
    private DataHolderQuizzesQueue quizzesQueue = new DataHolderQuizzesQueue();
}
