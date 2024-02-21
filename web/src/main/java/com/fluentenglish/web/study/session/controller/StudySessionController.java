package com.fluentenglish.web.study.session.controller;

import com.fluentenglish.web.study.session.controller.dto.StartStudySessionDto;
import com.fluentenglish.web.study.session.service.quiz.dto.AnswerSubmission;
import com.fluentenglish.web.study.session.service.StudySessionService;
import com.fluentenglish.web.study.session.service.dto.StudySessionSubmissionDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionInitializationDto;
import com.fluentenglish.web.study.session.service.dto.StudySessionUpdateDto;
import com.fluentenglish.web.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/study-sessions")
public class StudySessionController {

    private final StudySessionService studySessionService;

    private final UserService userService;

    public StudySessionController(StudySessionService studySessionService,
                                  UserService userService) {
        this.studySessionService = studySessionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> startStudySession(
            @RequestBody StartStudySessionDto startStudySessionDto) {
        int userId = getUserIdCurrentRequest();

        Set<Integer> wordIdsSet = new HashSet<>(startStudySessionDto.getWordIds());
        if (wordIdsSet.size() != startStudySessionDto.getWordIds().size()) {
            return ResponseEntity.badRequest().body("Word ids must be distinct");
        }

        StudySessionInitializationDto sessionInfo =
                studySessionService.startStudySession(userId, wordIdsSet);

        return ResponseEntity.ok(sessionInfo);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<Object> continueStudySession(@PathVariable String sessionId) {
        StudySessionUpdateDto sessionInfo = studySessionService
                .continueStudySession(sessionId);

        return ResponseEntity.ok(sessionInfo);
    }

    @PostMapping("/{sessionId}/answer")
    public ResponseEntity<Object> submitAnswer(@PathVariable String sessionId,
            @RequestBody AnswerSubmission answerSubmission) {
        StudySessionSubmissionDto sessionInfo = studySessionService.submitAnswer(sessionId, answerSubmission);

        return ResponseEntity.ok(sessionInfo);
    }

    private int getUserIdCurrentRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return userService.getUserIdByEmail(userEmail);
    }
}