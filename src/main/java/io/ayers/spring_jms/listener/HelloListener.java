package io.ayers.spring_jms.listener;

import io.ayers.spring_jms.config.JmsConfig;
import io.ayers.spring_jms.domain.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Slf4j
//@Component
@RequiredArgsConstructor
public class HelloListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {
        log.info("--- Received a message!");
        log.info(helloWorldMessage.toString());
    }

    @JmsListener(destination = JmsConfig.MY_SND_RCV_QUEUE)
    public void listenAndReturn(@Payload HelloWorldMessage helloWorldMessage,
                                @Headers MessageHeaders headers,
                                Message message) throws JMSException {
        log.info("--- Received a message!");

        HelloWorldMessage payloadMsg = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello Back!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
    }

}
