package com.authga.springaws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import io.awspring.cloud.messaging.config.annotation.EnableSqs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@EnableSqs
public class Config {
    @Primary
    @Bean
    public AmazonSQS amazonSQS(final AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonSQSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder
                                .EndpointConfiguration("http://localhost:4566", "us-east-1"))
                .withCredentials(awsCredentialsProvider)
                .build();
    }

    @Bean
    public AmazonSNS amazonSNS(final AWSCredentialsProvider awsCredentialsProvider) {
        final AmazonSNS amazonSNS = AmazonSNSClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4575", "us-east-1"))
                .withCredentials(awsCredentialsProvider)
                .build();
        amazonSNS.createTopic("topic");
        return amazonSNS;
    }

    @Primary
    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQSAsync) {
        var factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setAutoStartup(false);
        factory.setMaxNumberOfMessages(10);
        factory.setWaitTimeOut(20);
        return factory;
    }
}

