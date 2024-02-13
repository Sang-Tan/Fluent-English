package com.fluentenglish.web.gaming.user;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.gaming.user.dto.CurrentStateDto;
import com.fluentenglish.web.gaming.user.dto.LevelBeforeAfterDto;
import com.fluentenglish.web.gaming.user.dto.LevelProgressDto;
import com.fluentenglish.web.gaming.user.dto.UserAttributesDto;
import com.fluentenglish.web.gaming.user.level.UserLevel;
import com.fluentenglish.web.gaming.user.level.UserLevelRepository;
import com.fluentenglish.web.user.User;
import com.fluentenglish.web.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private final UserRepository userRepository;
    private final UserLevelRepository userLevelRepository;

    public UserInfoService(UserRepository userRepository,
                           UserLevelRepository userLevelRepository) {
        this.userRepository = userRepository;
        this.userLevelRepository = userLevelRepository;
    }

    public UserAttributesDto getUserAttributes(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserAttributesDto(user.getLevel().getBaseHp(), user.getLevel().getBaseShield());
    }

    public CurrentStateDto getCurrentState(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return new CurrentStateDto((int) (user.getCurrentHpPercent() * user.getLevel().getBaseHp()));
    }

    public LevelProgressDto getLevelProgress(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return new LevelProgressDto(user.getLevel().getLevel(), user.getExperience());
    }

    public LevelBeforeAfterDto addExperience(Integer userId, Integer experienceGained) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        LevelProgressDto before = new LevelProgressDto(user.getLevel().getLevel(), user.getExperience());

        user.setExperience(user.getExperience() + experienceGained);
        UserLevel nextLevel = userLevelRepository.findById(user.getLevel().getLevel() + 1).orElse(null);

        while(user.getExperience() >= user.getLevel().getExpToNextLevel() && nextLevel != null){
            user.setExperience(user.getExperience() - user.getLevel().getExpToNextLevel());
            user.setLevel(nextLevel);
            nextLevel = userLevelRepository.findById(user.getLevel().getLevel() + 1).orElse(null);
        }
        LevelProgressDto after = new LevelProgressDto(user.getLevel().getLevel(), user.getExperience());

        userRepository.save(user);
        return new LevelBeforeAfterDto(before, after);
    }

    public void updateCurrentHp(Integer userId, Integer currentHp) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        if(currentHp < 0 || currentHp > user.getLevel().getBaseHp()) {
            user.setCurrentHpPercent(currentHp/ (float) user.getLevel().getBaseHp());
            userRepository.save(user);
        }
    }

    public CurrentStateDto updateCurrentState(Integer userId, CurrentStateDto currentStateDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        if(currentStateDto.getCurrentHp() < 0 || currentStateDto.getCurrentHp() > user.getLevel().getBaseHp()) {
            user.setCurrentHpPercent(currentStateDto.getCurrentHp()/ (float) user.getLevel().getBaseHp());
            userRepository.save(user);
        }

        return new CurrentStateDto((int) (user.getCurrentHpPercent() * user.getLevel().getBaseHp()));
    }
}
