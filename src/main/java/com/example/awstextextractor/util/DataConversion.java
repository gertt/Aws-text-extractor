package com.example.awstextextractor.util;

import com.amazonaws.util.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public interface DataConversion {
    static ByteBuffer multipartToByteBuffer(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
        return ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
    }
}
