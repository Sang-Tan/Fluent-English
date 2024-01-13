package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer;

import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.MultipleChoiceAnswerDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice.AnswerChoices;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice.MediaChoices;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.MediaDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.MediaExtractor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AnswerMediaExtractor implements MediaExtractor<AnswerDto> {
    public List<MediaDto> extractMedia(AnswerDto answerDto) {
        if (answerDto instanceof MultipleChoiceAnswerDto multipleChoiceAnswerDto) {
            AnswerChoices<?> choices = multipleChoiceAnswerDto.getChoices();
            if (choices instanceof MediaChoices) {
                return ((MediaChoices<?>) choices).getData()
                        .stream()
                        .map(mediaDto -> (MediaDto) mediaDto)
                        .toList();
            }
        }
        return Collections.emptyList();
    }
}
