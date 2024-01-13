package com.fluentenglish.web.learningmaterial.quiz.admin.common.answer.multiplechoice.choice;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fluentenglish.web.learningmaterial.quiz.admin.common.media.AudioDto;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class AudioChoiceDto extends AudioDto {
}
