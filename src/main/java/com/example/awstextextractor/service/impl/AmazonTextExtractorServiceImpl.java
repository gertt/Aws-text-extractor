package com.example.awstextextractor.service.impl;

import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.example.awstextextractor.exeption.DetectDocumentException;
import com.example.awstextextractor.service.AmazonTextExtractorService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.awstextextractor.util.DataConversion.multipartToByteBuffer;

@Service
public class AmazonTextExtractorServiceImpl implements AmazonTextExtractorService {
    private final AmazonTextract amazonTextract;
    private final Logger log;

    public AmazonTextExtractorServiceImpl(AmazonTextract amazonTextract, Logger log) {
        this.amazonTextract = amazonTextract;
        this.log = log;
    }

    @Override
    public DetectDocumentTextResult getDetectDocumentTextResult(MultipartFile multipartFile) throws IOException {
        DetectDocumentTextRequest detectDocumentTextRequest = new DetectDocumentTextRequest()
                .withDocument(new Document()
                        .withBytes(multipartToByteBuffer(multipartFile)));

        DetectDocumentTextResult detectDocumentTextResult = amazonTextract.detectDocumentText(detectDocumentTextRequest);
        if (detectDocumentTextResult.getSdkHttpMetadata().getHttpStatusCode() != 200)
            throw new DetectDocumentException(multipartFile.getOriginalFilename());

        log.info("create DetectDocumentTextRequest ".concat(" filename: {} "), multipartFile.getOriginalFilename());
        return detectDocumentTextResult;
    }
}
