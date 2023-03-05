package com.example.awstextextractor.service;

import com.example.awstextextractor.model.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TextExtractService {
    ResponseEntity<CustomResponse> getTextExtracted(MultipartFile multipartFile) throws IOException;
}
