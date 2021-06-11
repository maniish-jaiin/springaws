package com.authga.springaws;

import cloud.localstack.docker.LocalstackDockerExtension;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@ExtendWith(LocalstackDockerExtension.class)
@LocalstackDockerProperties(services = { "s3", "sqs" })
public class SimpleMessageListenerIT {
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;
    private static final String QUEUE_NAME = "order-event-test-queue";

    @Test
    public void testMessageShouldBeUploadedToS3OnceConsumed() {
        String orderId = UUID.randomUUID().toString();
        String orderEvent = "MacBook";

        queueMessagingTemplate.convertAndSend(QUEUE_NAME, orderEvent);
    }
}
