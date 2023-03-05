package com.example.awstextextractor.exeption;

import com.example.awstextextractor.model.CustomResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log;

    public GlobalExceptionHandler(Logger log) {
        this.log = log;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleExceptions(Exception exception, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(false, exception.getMessage(), List.of());
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.name() + " url : {} customResponse: {}", exception, ((ServletWebRequest) request).getRequest().getRequestURI(), customResponse);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<CustomResponse> handleIOException(IOException ioException, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(false, ioException.getMessage(), List.of());
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.name() + " url : {} customResponse: {}", ioException, ((ServletWebRequest) request).getRequest().getRequestURI(), customResponse);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }

    @ExceptionHandler(DetectDocumentException.class)
    public ResponseEntity<CustomResponse> handleIOException(DetectDocumentException detectDocumentException, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(false, detectDocumentException.getMessage(), List.of());
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.name() + " url : {} customResponse: {}", detectDocumentException, ((ServletWebRequest) request).getRequest().getRequestURI(), customResponse);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }


    @ExceptionHandler(NoTextFoundException.class)
    public ResponseEntity<CustomResponse> handleNoTextFoundExeption(NoTextFoundException noTextFoundException, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(false, noTextFoundException.getMessage(), List.of());
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.name() + " url : {}", noTextFoundException, ((ServletWebRequest) request).getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }
}
