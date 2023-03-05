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

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        amazonTextExtractorService = new AmazonTextExtractorServiceImpl(amazonTextract, log);
    }

    @Test
    public void testGetDetectDocumentTextResult() throws IOException {
        String content = "Test file content";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt", // filename
                content.getBytes() // file content
        );

        ByteBuffer byteBuffer = DataConversion.multipartToByteBuffer(mockMultipartFile);
        Document document = new Document().withBytes(byteBuffer);
        DetectDocumentTextRequest detectDocumentTextRequest = new DetectDocumentTextRequest().withDocument(document);

        DetectDocumentTextResult detectDocumentTextResult = new DetectDocumentTextResult();

        HttpResponse httpResponse = new HttpResponse(null, null);
        httpResponse.setStatusCode(200);
        // create SdkHttpMetadata using the static from() method
        SdkHttpMetadata sdkHttpMetadata = SdkHttpMetadata.from(httpResponse);

        detectDocumentTextResult.setSdkHttpMetadata(sdkHttpMetadata);

        when(amazonTextract.detectDocumentText(detectDocumentTextRequest)).thenReturn(detectDocumentTextResult);

        DetectDocumentTextResult result = amazonTextExtractorService.getDetectDocumentTextResult(mockMultipartFile);

        assertEquals(detectDocumentTextResult, result);
    }

    @Test(expected = DetectDocumentException.class)
    public void testGetDetectDocumentTextResultError() throws IOException {
        String content = "Test file content";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt", // filename
                content.getBytes() // file content
        );

        ByteBuffer byteBuffer = DataConversion.multipartToByteBuffer(mockMultipartFile);
        Document document = new Document().withBytes(byteBuffer);
        DetectDocumentTextRequest detectDocumentTextRequest = new DetectDocumentTextRequest().withDocument(document);

        DetectDocumentTextResult detectDocumentTextResult = new DetectDocumentTextResult();

        HttpResponse httpResponse = new HttpResponse(null, null);
        httpResponse.setStatusCode(500);
        // create SdkHttpMetadata using the static from() method
        SdkHttpMetadata sdkHttpMetadata = SdkHttpMetadata.from(httpResponse);

        detectDocumentTextResult.setSdkHttpMetadata(sdkHttpMetadata);

        when(amazonTextract.detectDocumentText(detectDocumentTextRequest)).thenReturn(detectDocumentTextResult);

        amazonTextExtractorService.getDetectDocumentTextResult(mockMultipartFile);
    }
}
