package com.example.awstextextractor.service.impl;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.example.awstextextractor.exeption.DetectDocumentException;
import com.example.awstextextractor.util.DataConversion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AmazonTextExtractorServiceImplTest {

    @Mock
    private AmazonTextract amazonTextract;

    @Mock
    private Logger log;

    private AmazonTextExtractorServiceImpl amazonTextExtractorService;
    private MockMultipartFile mockMultipartFile;
    private Document document;
    private DetectDocumentTextRequest detectDocumentTextRequest;
    private DetectDocumentTextResult detectDocumentTextResult;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        amazonTextExtractorService = new AmazonTextExtractorServiceImpl(amazonTextract, log);
        mockMultipartFile = getMockMultipartFile();
        document = getDocument();
        detectDocumentTextRequest = getDetectDocumentTextRequest();
        detectDocumentTextResult = getDetectDocumentTextResult();
    }

    @Test
    public void testGetDetectDocumentTextResult() throws IOException {
        HttpResponse httpResponse = new HttpResponse(null, null);
        httpResponse.setStatusCode(HttpStatus.OK.value());
        SdkHttpMetadata sdkHttpMetadata = SdkHttpMetadata.from(httpResponse);
        detectDocumentTextResult.setSdkHttpMetadata(sdkHttpMetadata);

        when(amazonTextract.detectDocumentText(detectDocumentTextRequest)).thenReturn(detectDocumentTextResult);

        DetectDocumentTextResult result = amazonTextExtractorService.getDetectDocumentTextResult(mockMultipartFile);

        assertEquals(detectDocumentTextResult, result);
    }


    @Test(expected = DetectDocumentException.class)
    public void testGetDetectDocumentTextResultError() throws IOException {
        HttpResponse httpResponse = new HttpResponse(null, null);
        httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        SdkHttpMetadata sdkHttpMetadata = SdkHttpMetadata.from(httpResponse);

        detectDocumentTextResult.setSdkHttpMetadata(sdkHttpMetadata);

        when(amazonTextract.detectDocumentText(detectDocumentTextRequest)).thenReturn(detectDocumentTextResult);

        DetectDocumentTextResult result = amazonTextExtractorService.getDetectDocumentTextResult(mockMultipartFile);

        assertEquals(detectDocumentTextResult, result);
    }

    private DetectDocumentTextRequest getDetectDocumentTextRequest() {
        return new DetectDocumentTextRequest().withDocument(document);
    }

    private static DetectDocumentTextResult getDetectDocumentTextResult() {
        return new DetectDocumentTextResult();
    }

    private Document getDocument() throws IOException {
        ByteBuffer byteBuffer = DataConversion.multipartToByteBuffer(mockMultipartFile);
        return new Document().withBytes(byteBuffer);
    }

    private MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("test.txt", "Test file content".getBytes());
    }
}
