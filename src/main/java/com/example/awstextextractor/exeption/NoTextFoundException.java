package com.example.awstextextractor.exeption;

import java.io.Serial;

import static com.example.awstextextractor.util.AppConstants.NO_TEXT_FOUND;

public class NoTextFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NoTextFoundException(String id) {
        super(NO_TEXT_FOUND.concat(id));
    }
}

