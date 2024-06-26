package com.fluentenglish.web.gaming.user;

import com.fluentenglish.web.common.exception.errorresponse.NotFoundException;
import com.fluentenglish.web.gaming.chapter.Chapter;
import com.fluentenglish.web.gaming.chapter.ChapterRepository;
import com.fluentenglish.web.study.session.service.battle.dto.BeforeAfterStoryProgressDto;
import com.fluentenglish.web.gaming.user.dto.StoryContentDto;
import com.fluentenglish.web.gaming.user.dto.StoryProgressDto;
import com.fluentenglish.web.user.User;
import com.fluentenglish.web.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerProgressService {
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;
    public PlayerProgressService(UserRepository userRepository,
                                 ChapterRepository chapterRepository) {
        this.userRepository = userRepository;
        this.chapterRepository = chapterRepository;
    }
    public StoryProgressDto getStoryProgress(Integer userId) {
        return userRepository.findById(userId)
                .map(user -> new StoryProgressDto(user.getChapter().getNumber(), user.getChapterProgress()))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
    public BeforeAfterStoryProgressDto addChapterProgress(Integer userId, Float progress) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        StoryProgressDto before = new StoryProgressDto(user.getChapter().getNumber(), user.getChapterProgress());
        List<StoryContentDto> storyContentReceived = new ArrayList<>();

        if(user.getChapterProgress() + progress < 1.0f) {
            user.setChapterProgress(progress + user.getChapterProgress());
        } else {
            Optional<Chapter> nextChapter = chapterRepository.findById(user.getChapter().getNumber() + 1);
            if(nextChapter.isPresent()) {
                storyContentReceived.add(new StoryContentDto(user.getChapter().getStoryEndVi()
                        , user.getChapter().getStoryEndEng()));
                storyContentReceived.add(new StoryContentDto(nextChapter.get().getStoryStartVi()
                        , nextChapter.get().getStoryStartEng()));
                user.setChapter(nextChapter.get());
                user.setChapterProgress(0.0f);
            } else {
                storyContentReceived.add(new StoryContentDto(user.getChapter().getStoryEndVi()
                        , user.getChapter().getStoryEndEng()));
                user.setChapterProgress(1.0f);
            }
        }

        userRepository.save(user);
        StoryProgressDto after = new StoryProgressDto(user.getChapter().getNumber(), user.getChapterProgress());

        return new BeforeAfterStoryProgressDto(before, after, storyContentReceived);
    }
}
