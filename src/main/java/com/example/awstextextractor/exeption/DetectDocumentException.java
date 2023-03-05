package com.example.awstextextractor.exeption;

import java.io.Serial;

import static com.example.awstextextractor.util.AppConstants.DETECT_DOCUMENT_EXEPTION;

public class DetectDocumentException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DetectDocumentException(String id) {
        super(DETECT_DOCUMENT_EXEPTION.concat(id));
    }
}



