package com.example.awstextextractor.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextExtractConfiguration {
    @Value("${key.id}")
    private String keyID;
    @Value("${secret.key}")
    private String secretKey;

    @Bean
    public AmazonTextract amazonTextract() {
        AmazonTextractClientBuilder clientBuilder = AmazonTextractClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1);
        clientBuilder.setCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(keyID, secretKey)));
        return clientBuilder.build();
    }
}
