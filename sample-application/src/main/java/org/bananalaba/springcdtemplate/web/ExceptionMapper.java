package org.bananalaba.springcdtemplate.web;

import org.bananalaba.springcdtemplate.model.ErrorDto;
import org.bananalaba.springcdtemplate.service.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleException(final EntityNotFoundException exception) {
        return ResponseEntity.status(404).body(new ErrorDto(exception.getMessage()));
    }

}
