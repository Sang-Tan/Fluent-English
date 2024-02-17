package com.fluentenglish.web.study.session.battle;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.gaming.chapter.enemy.ChapterEnemy;
import com.fluentenglish.web.gaming.chapter.enemy.ChapterEnemyRepository;
import com.fluentenglish.web.gaming.user.PlayerProgressService;
import com.fluentenglish.web.gaming.user.UserInfoService;
import com.fluentenglish.web.gaming.user.dto.BeforeAfterStoryProgressDto;
import com.fluentenglish.web.gaming.user.dto.CurrentStateDto;
import com.fluentenglish.web.gaming.user.dto.LevelBeforeAfterDto;
import com.fluentenglish.web.gaming.user.dto.UserAttributesDto;
import com.fluentenglish.web.study.session.RedisUserStudySessionDao;
import com.fluentenglish.web.study.session.StudySession;
import org.springframework.stereotype.Service;

@Service
public class BattleServiceImpl implements BattleService{
    private final RedisUserStudySessionDao redisUserStudySessionDao;

    private final ChapterEnemyRepository chapterEnemyRepository;

    private final PlayerProgressService playerProgressService;

    private final UserInfoService userInfoService;

    public BattleServiceImpl(RedisUserStudySessionDao userStudySessionDao,
                             ChapterEnemyRepository chapterEnemyRepository,
                             PlayerProgressService playerProgressService,
                             UserInfoService userInfoService) {
        this.redisUserStudySessionDao = userStudySessionDao;
        this.chapterEnemyRepository = chapterEnemyRepository;
        this.playerProgressService = playerProgressService;
        this.userInfoService = userInfoService;
    }

    @Override
    public BattleInfo initializeBattle(String sessionId) {
        return initializeRedisBattle(sessionId);
    }

    @Override
    public BattleInfo updateBattle(String sessionId, Integer score) {
        return updateRedisBattle(sessionId, score);
    }

    @Override
    public BattleResult endBattle(String sessionId) {
        return endRedisBattle(sessionId);
    }

    private BattleInfo initializeRedisBattle(String sessionId) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        SessionBattle sessionBattle = studySession.getBattle();
        UserAttributesDto userAttributes = userInfoService.getUserAttributes(studySession.getUserId());
        int chapterNumber = playerProgressService.getStoryProgress(studySession.getUserId()).getChapterNumber();
        ChapterEnemy chapterEnemy = chapterEnemyRepository
                .findRandomByChapterNumber(chapterNumber)
                .orElseThrow(() -> new NotFoundException("There is no enemy in this chapter"));

        int userHp = userAttributes.getMaxHp();
        int userShield = userAttributes.getBaseShield();
        int userStreak = 0;
        int enemyDmg = chapterEnemy.getDamage();
        int expGain = chapterEnemy.getExpGain();
        String enemyName = chapterEnemy.getId().getEnemy().getName();

        BattleInfo battleInfo = BattleInfo.builder()
                .userHp(userHp)
                .userShield(userShield)
                .userStreak(userStreak)
                .enemyName(enemyName)
                .enemyDmg(enemyDmg)
                .expGain(expGain)
                .build();

        sessionBattle.setBattleInfo(battleInfo);
        return battleInfo;
    }

    private BattleInfo updateRedisBattle(String sessionId, Integer score) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        BattleInfo battleInfo = studySession.getBattle().getBattleInfo();

        int userHp = battleInfo.getUserHp();
        int userShield = battleInfo.getUserShield();
        int userStreak = battleInfo.getUserStreak();
        int enemyDmg = battleInfo.getEnemyDmg();
        boolean isLost = score == 0;

        // taking damage
        int dmgTaken = calculateDamageTaken(userStreak, score, enemyDmg);
        if (userShield > 0) {
            userShield -= dmgTaken;
            if (userShield < 0) {
                userHp += userShield;
                userShield = 0;
            }
        } else {
            userHp -= dmgTaken;
        }

        // updating user streak
        if(userStreak == 0){
            userStreak = isLost ? -1 : 1;
        }
        else if ((isLost && userStreak < 0) || (!isLost && userStreak > 0)) {
            userStreak = userStreak > 0 ? userStreak + 1 : userStreak - 1;
        } else {
            userStreak = 0;
        }

        battleInfo.setUserHp(userHp);
        battleInfo.setUserShield(userShield);
        battleInfo.setUserStreak(userStreak);

        return battleInfo;
    }

    private BattleResult endRedisBattle(String sessionId) {
        StudySession studySession = redisUserStudySessionDao.getSessionById(sessionId);
        BattleInfo battleInfo = studySession.getBattle().getBattleInfo();

        BeforeAfterStoryProgressDto chapterProgressDto = playerProgressService.addChapterProgress(studySession.getUserId(), getRandomChapterProgress(0.03f, 0.08f));
        LevelBeforeAfterDto levelProgressDto = userInfoService.addExperience(studySession.getUserId(), battleInfo.getExpGain());
        CurrentStateDto currentStateDto = userInfoService.updateCurrentState(studySession.getUserId(), new CurrentStateDto(battleInfo.getUserHp()));

        return new BattleResult(chapterProgressDto, levelProgressDto, currentStateDto);
    }

    private int calculateDamageTaken(int userStreak, int score, int enemyDmg) {
        return Math.toIntExact(Math.round(enemyDmg * (1 - userStreak * 0.1 + (10 - score) * 0.1)));
    }

    public Float getRandomChapterProgress(Float minPercent, Float maxPercent) {
        return (float) (Math.random() * (maxPercent - minPercent) + minPercent);
    }
}