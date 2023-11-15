package org.tierup.grading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AmazonSQSConfig {

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.create();
    }

}
