package com.fluentenglish.web.common.exception.userinput;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInputException extends RuntimeException{
    private final Map<String, List<InputErrorInfo>> errors;

    public UserInputException(List<InputErrorInfo> errors) {
        this.errors = errors.stream().collect(
                Collectors.groupingBy(InputErrorInfo::getField));
    }

    public Set<String> getErrorFields() {
        return errors.keySet();
    }

    public List<InputErrorInfo> getErrors(String field) {
        return errors.get(field);
    }
}
