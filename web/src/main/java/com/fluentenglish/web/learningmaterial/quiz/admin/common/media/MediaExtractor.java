package com.fluentenglish.web.learningmaterial.quiz.admin.common.media;

import java.util.List;

public interface MediaExtractor<T> {
    List<MediaDto> extractMedia(T dto);
}
