package com.fluentenglish.web.learningmaterial.quiz.admin.common.media;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImageDto.class, name = MediaConstants.TYPE_IMAGE),
        @JsonSubTypes.Type(value = AudioDto.class, name = MediaConstants.TYPE_AUDIO)
})
public abstract class MediaDto {
    protected String id;
    protected String url;
}
