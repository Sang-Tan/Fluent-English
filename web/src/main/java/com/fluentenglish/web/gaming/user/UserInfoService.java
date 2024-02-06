package com.fluentenglish.web.gaming.user;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.gaming.user.dto.*;
import com.fluentenglish.web.gaming.user.level.UserLevel;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private final UserRepository userRepository;

    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserAttributesDto getUserAttributes(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserAttributesDto(user.getLevel().getBaseHp(), user.getExperience());
    }

    public CurrentStateDto getCurrentState(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return new CurrentStateDto(user.getCurrentHp());
    }

    public LevelProgressDto getLevelProgress(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return new LevelProgressDto(user.getLevel().getLevel(), user.getExperience());
    }

    public void gainExperience(Integer userId, GainExperienceDto gainExperienceDto) {
        Integer experienceGained = gainExperienceDto.getExperienceGained();

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        UserLevel userLevel = user.getLevel();

        if(user.getExperience() + experienceGained >= userLevel.getExpToNextLevel()) {
            userLevel.setLevel(userLevel.getLevel() + 1);
            user.setExperience(user.getExperience() + experienceGained - userLevel.getExpToNextLevel());
        } else {
            user.setExperience(user.getExperience() + experienceGained);
        }
        userRepository.save(user);
    }
    public void updateCurrentHp(Integer userId, UpdateCurrentHpDto dto) {
        Integer currentHp = dto.getCurrentHp();

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setCurrentHp(currentHp);
        userRepository.save(user);
    }
}
