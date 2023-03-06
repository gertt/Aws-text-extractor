package com.example.awstextextractor.controller;

import com.example.awstextextractor.model.CustomResponse;
import com.example.awstextextractor.service.TextExtractService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextExtractControllerTest {

    @Mock
    private TextExtractService textExtractService;

    @Mock
    private Logger logger;

    private TextExtractController controller;

    private  MultipartFile mockFile;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new TextExtractController(textExtractService, logger);
        mockFile = mock(MultipartFile.class);
    }

    @Test
    public void testGetTextFromImageSuccess() throws IOException {
        List<String> mockData = Arrays.asList("Text", "More text");
        CustomResponse mockResponse = new CustomResponse(true, "Success", mockData);
        when(textExtractService.getTextExtracted(mockFile)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<CustomResponse> response = controller.getTextFromImage(mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    public void testGetTextFromImageFailure() throws IOException {
        CustomResponse mockResponse = new CustomResponse(false, "Failure", null);
        when(textExtractService.getTextExtracted(mockFile)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<CustomResponse> response = controller.getTextFromImage(mockFile);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }
}