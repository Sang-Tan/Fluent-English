package com.fluentenglish.web.learningmaterial.quiz.admin.common.question;

import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.MediaDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.MediaExtractor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class QuestionMediaExtractor implements MediaExtractor<QuestionDto> {
    @Override
    public List<MediaDto> extractMedia(QuestionDto question) {
        if (question.getAttachment() != null){
            return List.of(question.getAttachment());
        }

        return Collections.emptyList();
    }
}
