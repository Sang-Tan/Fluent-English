package com.fluentenglish.web.gaming.user;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.gaming.chapter.Chapter;
import com.fluentenglish.web.gaming.chapter.ChapterRepository;
import com.fluentenglish.web.gaming.user.dto.BeforeAfterStoryProcessDto;
import com.fluentenglish.web.gaming.user.dto.StoryProcessDto;
import com.fluentenglish.web.user.User;
import com.fluentenglish.web.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerProcessService {
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;
    public PlayerProcessService(UserRepository userRepository,
                                ChapterRepository chapterRepository) {
        this.userRepository = userRepository;
        this.chapterRepository = chapterRepository;
    }
    public StoryProcessDto getStoryProcess(Integer userId) {
        return userRepository.findById(userId)
                .map(user -> new StoryProcessDto(user.getChapter().getNumber(), user.getChapterProgress()))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
    public BeforeAfterStoryProcessDto addChapterProgress(Integer userId, Float progress) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        StoryProcessDto before = new StoryProcessDto(user.getChapter().getNumber(), user.getChapterProgress());

        if(user.getChapterProgress() + progress < 1.0f) {
            user.setChapterProgress(progress + user.getChapterProgress());
        } else if(user.getChapterProgress() + progress == 1.0f) {
            Optional<Chapter> nextChapter = chapterRepository.findById(user.getChapter().getNumber() + 1);
            if(nextChapter.isPresent()) {
                user.setChapter(nextChapter.get());
                user.setChapterProgress(0.0f);
            } else {
                user.setChapterProgress(1.0f);
            }
        }

        userRepository.save(user);
        StoryProcessDto after = new StoryProcessDto(user.getChapter().getNumber(), user.getChapterProgress());

        return new BeforeAfterStoryProcessDto(before, after);
    }
}