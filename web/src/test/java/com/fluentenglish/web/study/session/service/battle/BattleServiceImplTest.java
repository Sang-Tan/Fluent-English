package com.fluentenglish.web.study.session.service.battle;

import com.fluentenglish.web.study.session.dao.StudySession;
import com.fluentenglish.web.study.session.dao.StudySessionDao;
import com.fluentenglish.web.study.session.dao.battle.BattleInfo;
import com.fluentenglish.web.study.session.dao.battle.SessionBattle;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BattleServiceImplTest {
    @InjectMocks
    private BattleServiceImpl battleServiceUnderTest;

    @Mock
    private StudySessionDao studySessionDao;

    @Mock
    private BattleCalculator battleCalculator;

    @Mock
    private BattleDtoMapper battleDtoMapper;

    @ParameterizedTest
    @MethodSource("samplesProvider")
    void testUpdateBattle(BattleUpdateTestSample sample) {
        StudySession studySession = mock(StudySession.class);
        SessionBattle sessionBattle = mock(SessionBattle.class);

        BattleInfo beforeInfo = sample.getBefore();
        BattleInfo afterInfo = sample.getAfter();

        when(battleCalculator.calculateDamageTaken(anyInt(), anyInt(), anyInt())).thenReturn(sample.getMockDamage());
        when(studySessionDao.getSessionById(anyString())).thenReturn(studySession);
        when(studySession.getBattle()).thenReturn(sessionBattle);
        when(sessionBattle.getBattleInfo()).thenReturn(beforeInfo);

        ArgumentCaptor<BattleInfo> captor = ArgumentCaptor.forClass(BattleInfo.class);
        when(battleDtoMapper.toDto(captor.capture())).thenReturn(null);

        // Call the method to test
        battleServiceUnderTest.updateBattle(sample.getSessionId(), sample.getScore());
        BattleInfo updatedInfo = captor.getValue();

        assertEquals(afterInfo.getUserCurrentHp(), updatedInfo.getUserCurrentHp());
        assertEquals(afterInfo.getUserMaxHp(), updatedInfo.getUserMaxHp());
        assertEquals(afterInfo.getUserShield(), updatedInfo.getUserShield());
        assertEquals(afterInfo.getUserStreak(), updatedInfo.getUserStreak());
    }

    private static Stream<BattleUpdateTestSample> samplesProvider() {
        return Stream.of(
                testShieldTakeDamageSample(),
                testNoShieldTakeDamageSample(),
                testTakeDamageHpAndShieldSample(),
                testTakeDamageHpNotBelowZeroSample()
        );
    }

    private static BattleUpdateTestSample testShieldTakeDamageSample() {
        BattleUpdateTestSample sample = new BattleUpdateTestSample();

        sample.setMockDamage(10);
        sample.setScore(10);

        BattleInfo before = new BattleInfo();
        BattleInfo after = new BattleInfo();

        before.setUserMaxHp(100);
        after.setUserMaxHp(100);

        before.setUserCurrentHp(100);
        after.setUserCurrentHp(100);

        before.setUserShield(50);
        after.setUserShield(40);

        before.setUserStreak(3);
        after.setUserStreak(4);

        before.setEnemyDmg(10);
        after.setEnemyDmg(10);

        sample.setBefore(before);
        sample.setAfter(after);

        return sample;
    }

    private static BattleUpdateTestSample testNoShieldTakeDamageSample() {
        BattleUpdateTestSample sample = new BattleUpdateTestSample();

        sample.setMockDamage(10);
        sample.setScore(10);

        BattleInfo before = new BattleInfo();
        BattleInfo after = new BattleInfo();

        before.setUserMaxHp(100);
        after.setUserMaxHp(100);

        before.setUserCurrentHp(100);
        after.setUserCurrentHp(90);

        before.setUserShield(0);
        after.setUserShield(0);

        before.setUserStreak(3);
        after.setUserStreak(4);

        before.setEnemyDmg(10);
        after.setEnemyDmg(10);

        sample.setBefore(before);
        sample.setAfter(after);

        return sample;
    }

    private static BattleUpdateTestSample testTakeDamageHpAndShieldSample() {
        BattleUpdateTestSample sample = new BattleUpdateTestSample();

        sample.setScore(10);
        sample.setMockDamage(100);

        BattleInfo before = new BattleInfo();
        BattleInfo after = new BattleInfo();

        before.setUserMaxHp(100);
        after.setUserMaxHp(100);

        before.setUserCurrentHp(100);
        after.setUserCurrentHp(50);

        before.setUserShield(50);
        after.setUserShield(0);

        before.setUserStreak(3);
        after.setUserStreak(4);

        before.setEnemyDmg(10);
        after.setEnemyDmg(10);

        sample.setBefore(before);
        sample.setAfter(after);

        return sample;
    }

    private static BattleUpdateTestSample testTakeDamageHpNotBelowZeroSample() {
        BattleUpdateTestSample sample = new BattleUpdateTestSample();

        sample.setScore(10);
        sample.setMockDamage(100);

        BattleInfo before = new BattleInfo();
        BattleInfo after = new BattleInfo();

        before.setUserMaxHp(100);
        after.setUserMaxHp(100);

        before.setUserCurrentHp(50);
        after.setUserCurrentHp(0);

        before.setUserShield(0);
        after.setUserShield(0);

        before.setUserStreak(3);
        after.setUserStreak(4);

        before.setEnemyDmg(10);
        after.setEnemyDmg(10);

        sample.setBefore(before);
        sample.setAfter(after);

        return sample;
    }

    @Getter
    @Setter
    private static class BattleUpdateTestSample {
        private BattleInfo before;
        private BattleInfo after;
        private String sessionId = "FOO";
        private int score = 10;
        private int mockDamage = 10;
    }
}