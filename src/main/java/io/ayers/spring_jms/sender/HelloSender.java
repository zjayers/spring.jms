package io.ayers.spring_jms.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ayers.spring_jms.config.JmsConfig;
import io.ayers.spring_jms.domain.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        log.info("--- Sending a message...");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        log.info("--- Message Sent!");
    }

//    @Scheduled(fixedRate = 2000)
//    public void sendAndReceiveMessage() throws JMSException {
//        log.info("--- Sending a message...");
//
//        HelloWorldMessage message = HelloWorldMessage.builder()
//                .id(UUID.randomUUID())
//                .message("Hello World!")
//                .build();
//
//        Message recievedJmsTemplate = jmsTemplate.sendAndReceive(JmsConfig.MY_SND_RCV_QUEUE, new MessageCreator() {
//
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                Message helloMsg = null;
//                try {
//                    helloMsg = session.createTextMessage(objectMapper.writeValueAsString(message));
//                    helloMsg.setStringProperty("_type", "io.ayers.spring_jms.domain.HelloWorldMessage");
//                    return helloMsg;
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                    throw new JMSException("ERROR!");
//                }
//            }
//
//        });
//
//        if (recievedJmsTemplate != null) {
//            log.info(recievedJmsTemplate.getBody(String.class));
//        }
//    }

}
