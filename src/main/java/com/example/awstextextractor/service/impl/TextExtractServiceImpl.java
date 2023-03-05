package com.example.awstextextractor.service.impl;

import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.example.awstextextractor.exeption.NoTextFoundException;
import com.example.awstextextractor.model.CustomResponse;
import com.example.awstextextractor.service.AmazonTextExtractorService;
import com.example.awstextextractor.service.TextExtractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.awstextextractor.util.AppConstants.*;

@Service
public class TextExtractServiceImpl implements TextExtractService {
    private final AmazonTextExtractorService amazonTextExtractorService;

    public TextExtractServiceImpl(AmazonTextExtractorService amazonTextExtractorService) {
        this.amazonTextExtractorService = amazonTextExtractorService;
    }

    @Override
    public ResponseEntity<CustomResponse> getTextExtracted(MultipartFile multipartFile) throws IOException {
        DetectDocumentTextResult detectDocumentTextResult = amazonTextExtractorService.getDetectDocumentTextResult(multipartFile);
        List<String> textsFromImage = detectDocumentTextResult.getBlocks().stream()
                .filter(block -> block.getBlockType().equals("LINE"))
                .map(Block::getText).toList();

        if (textsFromImage.isEmpty())
            throw new NoTextFoundException(multipartFile.getOriginalFilename());

        CustomResponse customResponse = new CustomResponse(true, IMAGE_CONVERTED_SUCCESSFULLY, textsFromImage);
        return ResponseEntity.status(HttpStatus.OK).body(customResponse);
    }
}
