package com.authga.springaws;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageReceiver {

    @SqsListener(value = "${sqs.orderEventQueue}")
    public void receiveMessage(String message, @Header("SenderId") String senderId) {
        log.info("message received {} {}",senderId,message);
    }
}
