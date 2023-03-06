package com.example.awstextextractor.service.impl;

import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.example.awstextextractor.exeption.NoTextFoundException;
import com.example.awstextextractor.model.CustomResponse;
import com.example.awstextextractor.service.AmazonTextExtractorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.awstextextractor.util.AppConstants.IMAGE_CONVERTED_SUCCESSFULLY;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TextExtractServiceImplTest {

    @Mock
    private AmazonTextExtractorService amazonTextExtractorService;

    private TextExtractServiceImpl textExtractService;
    private MockMultipartFile mockFile;

    @Before
    public void setUp() {
        textExtractService = new TextExtractServiceImpl(amazonTextExtractorService);
        mockFile = getMockMultipartFile();
    }

    @Test
    public void testGetTextExtractedSuccess() throws IOException {
        List<Block> mockBlocks = new ArrayList<>();
        Block mockBlock1 = new Block().withBlockType("LINE").withText("Some text");
        Block mockBlock2 = new Block().withBlockType("LINE").withText("Some more text");
        mockBlocks.add(mockBlock1);
        mockBlocks.add(mockBlock2);

        DetectDocumentTextResult mockDetectResult = new DetectDocumentTextResult().withBlocks(mockBlocks);
        when(amazonTextExtractorService.getDetectDocumentTextResult(any(MultipartFile.class))).thenReturn(mockDetectResult);

        // call method being tested
        ResponseEntity<CustomResponse> responseEntity = textExtractService.getTextExtracted(mockFile);

        // verify the result
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(IMAGE_CONVERTED_SUCCESSFULLY, Objects.requireNonNull(responseEntity.getBody()).message());
        assertEquals(Arrays.asList("Some text", "Some more text"), responseEntity.getBody().data());
    }

    @Test(expected = NoTextFoundException.class)
    public void testGetTextExtractedNoTextFound() throws IOException {
        DetectDocumentTextResult mockDetectResult = new DetectDocumentTextResult().withBlocks(new ArrayList<>());
        when(amazonTextExtractorService.getDetectDocumentTextResult(any(MultipartFile.class))).thenReturn(mockDetectResult);

        // call method being tested
        textExtractService.getTextExtracted(mockFile);
    }

    private static MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("test-image.jpg", new byte[10]);
    }

}