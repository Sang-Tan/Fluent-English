package com.fluentenglish.web.spacedrepetition.word;

import com.fluentenglish.web.learning.material.word.Word;
import com.fluentenglish.web.spacedrepetition.fsrs.FSRSScheduler;
import com.fluentenglish.web.spacedrepetition.fsrs.MaterialMemoDto;
import com.fluentenglish.web.spacedrepetition.word.dto.UpdateWordMemoDto;
import com.fluentenglish.web.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WordMemoServiceImpl implements WordMemoService {
    private final WordMemoRepository wordMemoRepository;

    private final FSRSScheduler fsrsScheduler;

    private final WordMemoMapper wordMemoMapper;

    private final WordMemoSpecs wordMemoSpecs;

    public WordMemoServiceImpl(WordMemoRepository wordMemoRepository,
                               FSRSScheduler fsrsScheduler,
                               WordMemoMapper wordMemoMapper,
                               WordMemoSpecs wordMemoSpecs) {
        this.wordMemoRepository = wordMemoRepository;
        this.fsrsScheduler = fsrsScheduler;
        this.wordMemoMapper = wordMemoMapper;
        this.wordMemoSpecs = wordMemoSpecs;
    }

    @Override
    public void updateWordMemo(UpdateWordMemoDto updateWordMemoDto) {
        WordMemoId wordMemoId = new WordMemoId(
                new User(updateWordMemoDto.getUserId()),
                new Word(updateWordMemoDto.getWordId())
        );

        Optional<WordMemo> wordMemoOpt = wordMemoRepository.findById(wordMemoId);

        if (wordMemoOpt.isPresent()) {
            updateExistingWordMemo(updateWordMemoDto, wordMemoOpt.get());
        } else {
            saveNewWordMemo(updateWordMemoDto, wordMemoId);
        }
    }

    @Override
    public List<Integer> getNeedToStudyWordIds(int userId, int limit) {
        Specification<WordMemo> spec = wordMemoSpecs.userIdEquals(userId)
                .and(wordMemoSpecs.nextStudyAtLeast(new Date()));

        Pageable pageable = PageRequest.of(0, limit,
                Sort.by(Sort.Direction.ASC, "nextStudy"));

        return wordMemoRepository.findAll(spec, pageable).stream()
                .map(wordMemo -> wordMemo.getId().getWord().getId())
                .toList();
    }

    private void saveNewWordMemo(UpdateWordMemoDto updateDto, WordMemoId wordMemoId) {
        MaterialMemoDto materialMemoDto =
                fsrsScheduler.learnMaterial(updateDto.getGrade(), System.currentTimeMillis());
        WordMemo wordMemo = wordMemoMapper.toEntity(materialMemoDto);
        wordMemo.setId(wordMemoId);

        wordMemoRepository.save(wordMemo);
    }

    private void updateExistingWordMemo(UpdateWordMemoDto updateDto, WordMemo wordMemo) {
        MaterialMemoDto materialMemoDto =
                fsrsScheduler.learnMaterial(updateDto.getGrade(), System.currentTimeMillis());
        wordMemoMapper.updateEntity(materialMemoDto, wordMemo);

        wordMemoRepository.save(wordMemo);
    }
}
