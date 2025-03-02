package com.doctorvisits.visittracker.exception.handler;

import com.doctorvisits.visittracker.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(Exception ex) {
        return ResponseEntity.badRequest().body("{\"error\": \"" + ex.getMessage() + "\"}");
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(DateTimeParseException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid date/time format: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
