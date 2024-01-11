package com.fluentenglish.web.common.exception.errorresponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Log4j2
@RestControllerAdvice
public class ResponseExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .withMessage(ex.getMessage())
                .build());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                .withMessage(ex.getMessage())
                .build());
    }

    @ExceptionHandler(UserInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleUserInputException(UserInputException ex) {
        ErrorResponse.Builder errorBuilder = ErrorResponse.builder()
                .withMessage(ex.getMessage());

        List<String> errorFields = ex.getErrorFields();
        if (errorFields != null && !errorFields.isEmpty()) {
            ObjectNode error = objectMapper.createObjectNode();
            for (String field : errorFields) {
                String firstError = ex.getFirstErrorMessage(field);
                error.put(field, firstError != null ? firstError : "");
            }
            errorBuilder.withError(error);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBuilder.build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Void> handleAllUncaughtException(Exception ex) {
        log.error("Uncaught exception [ERROR: {}]", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
