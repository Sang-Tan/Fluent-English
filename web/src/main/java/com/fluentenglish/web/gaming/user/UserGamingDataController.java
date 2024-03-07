package com.fluentenglish.web.gaming.user;

import com.fluentenglish.web.gaming.user.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserGamingDataController {
    private final UserInfoService userInfoService;

    private final PlayerProgressService playerProgressService;

    public UserGamingDataController(UserInfoService userInfoService, PlayerProgressService playerProgressService) {
        this.userInfoService = userInfoService;
        this.playerProgressService = playerProgressService;
    }

    @GetMapping("/users/{userId}/gaming")
    public ResponseEntity<GamingDataDto> getGamingData(@PathVariable Integer userId) {
        LevelProgressDto levelProgress = userInfoService.getLevelProgress(userId);
        StoryProgressDto storyProgress = playerProgressService.getStoryProgress(userId);
        UserAttributesDto userAttributes = userInfoService.getUserAttributes(userId);
        CurrentStateDto currentState = userInfoService.getCurrentState(userId);

        GamingDataDto gamingDataDto = new GamingDataDto(levelProgress, storyProgress, userAttributes, currentState);
        return ResponseEntity.ok(gamingDataDto);
    }
}
