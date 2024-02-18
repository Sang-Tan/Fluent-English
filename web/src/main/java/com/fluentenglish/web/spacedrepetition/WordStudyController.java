package com.fluentenglish.web.spacedrepetition;

import com.fluentenglish.web.spacedrepetition.word.WordMemoService;
import com.fluentenglish.web.spacedrepetition.word.dto.IgnoreWordsDto;
import com.fluentenglish.web.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WordStudyController {
    private final WordMemoService wordMemoService;
    
    private final UserService userService;

    public WordStudyController(WordMemoService wordMemoService, UserService userService) {
        this.wordMemoService = wordMemoService;
        this.userService = userService;
    }
    
    @PostMapping("/ignore-words")
    public ResponseEntity<Void> ignoreWords(@RequestBody IgnoreWordsDto ignoreWordsDto) {
        int userId = getCurrentUserId();

        wordMemoService.ignoreWords(userId, ignoreWordsDto);

        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/ignore-words/{wordId}")
    public ResponseEntity<Void> reinstateWord(@PathVariable int wordId) {
        int userId = getCurrentUserId();
        
        wordMemoService.reinstateWord(userId, wordId);

        return ResponseEntity.noContent().build();
    }

    private int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return userService.getUserIdByEmail(userEmail);
    }
}