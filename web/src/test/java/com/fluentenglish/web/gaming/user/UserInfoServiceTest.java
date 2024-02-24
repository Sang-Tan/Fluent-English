package com.fluentenglish.web.gaming.user;

import com.fluentenglish.web.study.session.service.battle.dto.LevelBeforeAfterDto;
import com.fluentenglish.web.gaming.user.dto.LevelProgressDto;
import com.fluentenglish.web.gaming.user.level.UserLevel;
import com.fluentenglish.web.gaming.user.level.UserLevelRepository;
import com.fluentenglish.web.user.User;
import com.fluentenglish.web.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {
    @InjectMocks
    private UserInfoService userInfoService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLevelRepository userLevelRepository;

    @Test
    void testAddExperience_expNotEnoughToLevelUp_shouldAddExp() {
        // given
        int userId = 1;
        int experienceGained = 10;
        UserLevel level1 = userLevel1();
        given(userRepository.findById(userId)).willReturn(Optional.of(createUserWithLevel1(userId)));
        mockUserLevelRepositoryFindById();

        // when
        LevelBeforeAfterDto beforeAfterDto = userInfoService.addExperience(userId, experienceGained);

        // then
        LevelProgressDto before = beforeAfterDto.getBefore();
        LevelProgressDto after = beforeAfterDto.getAfter();
        assertThat(before.getLevel()).isEqualTo(1);
        assertThat(before.getExperience()).isEqualTo(0);
        assertThat(before.getExpToNextLevel()).isEqualTo(level1.getExpToNextLevel());

        assertThat(after.getLevel()).isEqualTo(1);
        assertThat(after.getExperience()).isEqualTo(10);
        assertThat(after.getExpToNextLevel()).isEqualTo(level1.getExpToNextLevel());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAddExperience_expEnoughToLevelUp_shouldLevelUp() {
        // given
        UserLevel level1 = userLevel1();
        UserLevel level2 = userLevel2();
        int expAfterLevelUp = 10;
        int userId = 1;
        int experienceGained = level1.getExpToNextLevel() + expAfterLevelUp;

        given(userRepository.findById(userId)).willReturn(Optional.of(createUserWithLevel1(userId)));
        mockUserLevelRepositoryFindById();

        // when
        LevelBeforeAfterDto beforeAfterDto = userInfoService.addExperience(userId, experienceGained);

        // then
        LevelProgressDto before = beforeAfterDto.getBefore();
        LevelProgressDto after = beforeAfterDto.getAfter();

        assertThat(before.getLevel()).isEqualTo(1);
        assertThat(before.getExperience()).isEqualTo(0);
        assertThat(before.getExpToNextLevel()).isEqualTo(level1.getExpToNextLevel());

        assertThat(after.getLevel()).isEqualTo(2);
        assertThat(after.getExperience()).isEqualTo(expAfterLevelUp);
        assertThat(after.getExpToNextLevel()).isEqualTo(level2.getExpToNextLevel());

        verify(userRepository).save(any(User.class));
    }

    private User createUserWithLevel1(int id) {
        User user = new User();
        user.setId(id);
        user.setExperience(0);
        user.setLevel(userLevel1());

        return user;
    }

    private UserLevel userLevel1() {
        UserLevel userLevel = new UserLevel();
        userLevel.setLevel(1);
        userLevel.setExpToNextLevel(100);

        return userLevel;
    }

    private UserLevel userLevel2() {
        UserLevel userLevel = new UserLevel();
        userLevel.setLevel(2);
        userLevel.setExpToNextLevel(200);

        return userLevel;
    }

    private void mockUserLevelRepositoryFindById() {
        when(userLevelRepository.findById(anyInt()))
                .thenAnswer(invocation -> {
                    int level = invocation.getArgument(0);
                    if (level == 1) {
                        return Optional.of(userLevel1());
                    } else if (level == 2) {
                        return Optional.of(userLevel2());
                    }
                    return Optional.empty();
                });
    }

}