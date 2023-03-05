package com.example.awstextextractor.controller;

import com.example.awstextextractor.model.CustomResponse;
import com.example.awstextextractor.service.TextExtractService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.awstextextractor.util.AppConstants.*;

@RestController
public class TextExtractController {
    private final TextExtractService textExtractService;
    private final Logger log;

    public TextExtractController(TextExtractService textExtractService, Logger log) {
        this.textExtractService = textExtractService;
        this.log = log;
    }

    @PostMapping(PATH_UPLOAD)
    public ResponseEntity<CustomResponse> getTextFromImage(@RequestParam("img") MultipartFile multipartFile) throws IOException {
        log.info(START_SERVICE.concat("url: {}, filename: {} "), PATH_UPLOAD, multipartFile.getOriginalFilename());
        ResponseEntity<CustomResponse> customResponseResponse = textExtractService.getTextExtracted(multipartFile);
        log.info(STOP_SERVICE.concat("url: {}, response: {}, filename: {} "), PATH_UPLOAD, customResponseResponse, multipartFile.getOriginalFilename());
        return customResponseResponse;
    }
}
