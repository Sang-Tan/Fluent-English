package com.fluentenglish.web.study.session.controller;

import com.fluentenglish.web.study.session.controller.dto.ExistStudySessionRequestDto;
import com.fluentenglish.web.study.session.controller.dto.StartStudySessionDto;
import com.fluentenglish.web.study.session.controller.dto.SubmitAnswerDto;
import com.fluentenglish.web.study.session.service.StudySessionService;
import com.fluentenglish.web.study.session.service.dto.StudySessionInfo;
import com.fluentenglish.web.study.session.service.dto.StudySessionUpdateInfo;
import com.fluentenglish.web.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/study-session")
@Secured("ROLE_USER")
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

        StudySessionUpdateInfo sessionInfo =
                studySessionService.startStudySession(userId, wordIdsSet);

        return ResponseEntity.ok(sessionInfo);
    }

    @PostMapping("/continue")
    public ResponseEntity<Object> continueStudySession(
            @RequestBody @Valid ExistStudySessionRequestDto sessionRequestDto) {
        StudySessionUpdateInfo sessionInfo = studySessionService
                .continueStudySession(sessionRequestDto.getSessionId());

        return ResponseEntity.ok(sessionInfo);
    }

    @PostMapping("/answer")
    public ResponseEntity<Object> submitAnswer(
            @RequestBody @Valid SubmitAnswerDto answerSubmission) {
        StudySessionInfo sessionInfo = studySessionService.submitAnswer(
                answerSubmission.getSessionId(), answerSubmission.getAnswer());

        return ResponseEntity.ok(sessionInfo);
    }

    private int getUserIdCurrentRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return userService.getUserIdByEmail(userEmail);
    }
}
