package com.example.awstextextractor.service;

import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmazonTextExtractorService {
    DetectDocumentTextResult getDetectDocumentTextResult(MultipartFile multipartFile) throws IOException;
}
