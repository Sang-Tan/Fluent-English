package com.fluentenglish.web.study.spacedrepetition;

import com.fluentenglish.web.spacedrepetition.fsrs.Grade;
import com.fluentenglish.web.spacedrepetition.word.WordMemoService;
import com.fluentenglish.web.spacedrepetition.word.dto.UpdateWordMemoDto;
import com.fluentenglish.web.study.session.StudySession;
import com.fluentenglish.web.study.session.UserStudySessionDao;
import com.fluentenglish.web.study.session.score.SessionWordsScores;
import com.fluentenglish.web.study.session.score.WordScore;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RedisSRSessionService implements SRSessionService {
    private final UserStudySessionDao userStudySessionDao;

    private final WordMemoService wordMemoService;

    public RedisSRSessionService(UserStudySessionDao userStudySessionDao,
                                 WordMemoService wordMemoService) {
        this.userStudySessionDao = userStudySessionDao;
        this.wordMemoService = wordMemoService;
    }

    @Override
    public void initialize(String sessionId, Set<Integer> wordIds) {
        SessionWordsScores sessionWordsScores =
                userStudySessionDao.getSessionById(sessionId).getWordsScores();
        Map<Integer, WordScore> wordsScores = wordIds.stream()
                .collect(Collectors.toMap(
                        wordId -> wordId,
                        wordId -> {
                            WordScore wordScore = new WordScore();
                            wordScore.setTotalScore(0);
                            wordScore.setAttempts(0);

                            return wordScore;
                        }));
        sessionWordsScores.setWordsScores(wordsScores);
    }

    @Override
    public void addAttempt(String sessionId, int wordId, int score) {
        validateScore(score);

        StudySession studySession = userStudySessionDao.getSessionById(sessionId);
        SessionWordsScores sessionWordsScores =
                studySession.getWordsScores();

        WordScore wordScore = sessionWordsScores.getWordScore(wordId);
        wordScore.setTotalScore(wordScore.getTotalScore() + score);
        wordScore.setAttempts(wordScore.getAttempts() + 1);

        sessionWordsScores.setWordScore(wordId, wordScore);
    }

    @Override
    public WordsScoresResult endSession(String sessionId) {
        StudySession studySession = userStudySessionDao.getSessionById(sessionId);
        int userId = studySession.getUserId();
        SessionWordsScores sessionWordsScores =
                studySession.getWordsScores();
        Map<Integer, WordScore> wordsScores = sessionWordsScores.getWordsScores();

        WordsScoresResult wordsScoresResult = new WordsScoresResult();
        wordsScores.forEach((wordId, wordScore) -> {
            int avgScore = wordScore.getAttempts() == 0 ? 0 :
                    Math.round((float) wordScore.getTotalScore() / wordScore.getAttempts());
            wordsScoresResult.getScoresByWords().put(wordId, avgScore);

            updateWordMemo(wordId, userId, avgScore);
        });

        return wordsScoresResult;
    }

    private void validateScore(int score) {
        if (score < 0 || score > 10) {
            throw new IllegalArgumentException("Score must be between 0 and 10");
        }
    }

    private void updateWordMemo(int wordId, int userId, int score) {
        UpdateWordMemoDto updateWordMemoDto = new UpdateWordMemoDto();
        updateWordMemoDto.setWordId(wordId);
        updateWordMemoDto.setUserId(userId);
        if (score >= 9) {
            updateWordMemoDto.setGrade(Grade.EASY);
        } else if (score >= 7) {
            updateWordMemoDto.setGrade(Grade.GOOD);
        } else if (score >= 5) {
            updateWordMemoDto.setGrade(Grade.HARD);
        } else {
            updateWordMemoDto.setGrade(Grade.AGAIN);
        }
        wordMemoService.updateWordMemo(updateWordMemoDto);
    }
}
