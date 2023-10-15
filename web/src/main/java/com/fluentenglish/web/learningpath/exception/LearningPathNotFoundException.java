package com.fluentenglish.web.learningpath.exception;

public class LearningPathNotFoundException extends RuntimeException {
    public LearningPathNotFoundException(Integer id) {
        super("Could not find learning path " + id);
    }

}
