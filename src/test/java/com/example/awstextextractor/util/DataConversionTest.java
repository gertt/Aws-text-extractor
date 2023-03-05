package com.example.awstextextractor.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.ByteBuffer;

@RunWith(SpringRunner.class)
public class DataConversionTest {

    @Test
    public void testMultipartToByteBuffer() throws Exception {
        String content = "Test file content";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt", // filename
                content.getBytes() // file content
        );

        ByteBuffer byteBuffer = DataConversion.multipartToByteBuffer(mockMultipartFile);
        String result = new String(byteBuffer.array());

        Assert.assertEquals(content, result);
    }
}