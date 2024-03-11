package com.fluentenglish.web.study.session.service.battle;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.gaming.chapter.enemy.ChapterEnemy;
import com.fluentenglish.web.gaming.chapter.enemy.ChapterEnemyRepository;
import com.fluentenglish.web.gaming.user.PlayerProgressService;
import com.fluentenglish.web.gaming.user.UserInfoService;
import com.fluentenglish.web.gaming.user.dto.CurrentStateDto;
import com.fluentenglish.web.gaming.user.dto.UserAttributesDto;
import com.fluentenglish.web.study.session.dao.StudySession;
import com.fluentenglish.web.study.session.dao.StudySessionDao;
import com.fluentenglish.web.study.session.dao.battle.BattleInfo;
import com.fluentenglish.web.study.session.dao.battle.SessionBattle;
import com.fluentenglish.web.study.session.service.battle.dto.*;
import org.springframework.stereotype.Service;

@Service
public class BattleServiceImpl implements BattleService {
    private static final float CHAPTER_PROGRESS_MIN_PERCENT = 0.03f;
    private static final float CHAPTER_PROGRESS_MAX_PERCENT = 0.08f;

    private final StudySessionDao studySessionDao;

    private final ChapterEnemyRepository chapterEnemyRepository;

    private final PlayerProgressService playerProgressService;

    private final UserInfoService userInfoService;

    private final BattleDtoMapper battleDtoMapper;

    public BattleServiceImpl(StudySessionDao studySessionDao,
                             ChapterEnemyRepository chapterEnemyRepository,
                             PlayerProgressService playerProgressService,
                             UserInfoService userInfoService,
                             BattleDtoMapper battleDtoMapper) {
        this.studySessionDao = studySessionDao;
        this.chapterEnemyRepository = chapterEnemyRepository;
        this.playerProgressService = playerProgressService;
        this.userInfoService = userInfoService;
        this.battleDtoMapper = battleDtoMapper;
    }

    @Override
    public BattleInfoDto initializeBattle(String sessionId) {
        BattleInfo battleInfo = initializeRedisBattle(sessionId);
        return battleDtoMapper.toDto(battleInfo);
    }

    @Override
    public BattleInfoDto updateBattle(String sessionId, Integer score) {
        BattleInfo battleInfo = updateRedisBattle(sessionId, score);
        return battleDtoMapper.toDto(battleInfo);
    }

    @Override
    public BattleInfoDto getBattleInfo(String sessionId) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        BattleInfo battleInfo = studySession.getBattle().getBattleInfo();
        return battleDtoMapper.toDto(battleInfo);
    }

    @Override
    public BattleResult endBattle(String sessionId) {
        return endRedisBattle(sessionId);
    }

    private BattleInfo initializeRedisBattle(String sessionId) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        int userId = studySession.getUserId();
        SessionBattle sessionBattle = studySession.getBattle();
        UserAttributesDto userAttributes = userInfoService.getUserAttributes(userId);
        CurrentStateDto currentState = userInfoService.getCurrentState(userId);
        int chapterNumber = playerProgressService.getStoryProgress(userId).getChapterNumber();
        ChapterEnemy chapterEnemy = chapterEnemyRepository
                .findRandomByChapterNumber(chapterNumber)
                .orElseThrow(() -> new NotFoundException("There is no enemy in this chapter"));

        int currentHp = currentState.getCurrentHp();
        int userMaxHp = userAttributes.getMaxHp();
        int userShield = userAttributes.getBaseShield();
        int userStreak = 0;
        int enemyDmg = chapterEnemy.getDamage();
        int expGain = chapterEnemy.getExpGain();
        String enemyName = chapterEnemy.getId().getEnemy().getName();

        BattleInfo battleInfo = BattleInfo.builder()
                .userCurrentHp(currentHp)
                .userMaxHp(userMaxHp)
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
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        BattleInfo battleInfo = studySession.getBattle().getBattleInfo();

        int userHp = battleInfo.getUserCurrentHp();
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
        if (userStreak == 0) {
            userStreak = isLost ? -1 : 1;
        } else if ((isLost && userStreak < 0) || (!isLost && userStreak > 0)) {
            userStreak = userStreak > 0 ? userStreak + 1 : userStreak - 1;
        } else {
            userStreak = 0;
        }

        battleInfo.setUserCurrentHp(userHp);
        battleInfo.setUserShield(userShield);
        battleInfo.setUserStreak(userStreak);

        studySession.getBattle().setBattleInfo(battleInfo);

        return battleInfo;
    }

    private BattleResult endRedisBattle(String sessionId) {
        StudySession studySession = studySessionDao.getSessionById(sessionId);
        BattleInfo battleInfo = studySession.getBattle().getBattleInfo();
        int userId = studySession.getUserId();

        AttributesBeforeAfterDto attributesChange = new AttributesBeforeAfterDto();
        attributesChange.setBefore(userInfoService.getUserAttributes(userId));

        BeforeAfterStoryProgressDto chapterProgressDto = playerProgressService
                .addChapterProgress(userId, getRandomChapterProgress());

        LevelBeforeAfterDto levelProgressDto = userInfoService
                .addExperience(userId, battleInfo.getExpGain());

        CurrentStateDto currentStateDto = userInfoService
                .updateCurrentState(userId, new CurrentStateDto(battleInfo.getUserCurrentHp()));

        attributesChange.setAfter(userInfoService.getUserAttributes(userId));

        return BattleResult.builder()
                .storyProgress(chapterProgressDto)
                .levelProgress(levelProgressDto)
                .attributesChange(attributesChange)
                .currentState(currentStateDto)
                .build();
    }

    private int calculateDamageTaken(int userStreak, int score, int enemyDmg) {
        return Math.toIntExact(Math.round(enemyDmg * (1 - userStreak * 0.1 + (10 - score) * 0.1)));
    }

    private Float getRandomChapterProgress() {
        return (float) (Math.random() * (CHAPTER_PROGRESS_MAX_PERCENT - CHAPTER_PROGRESS_MIN_PERCENT) + CHAPTER_PROGRESS_MIN_PERCENT);
    }


}
