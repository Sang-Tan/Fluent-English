package com.fluentenglish.web.learningpath.exception;

public class LearningPathAlreadyExistsException extends RuntimeException {
    public LearningPathAlreadyExistsException(String name) {
        super("Learning path " + name + " already exists");
    }
}
