package com.furb.phaorder.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class CustomMessageSender {

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Object object, String exchange, String routingKey) {
        var messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        var message = new Message(objectMapper.writeValueAsBytes(object), messageProperties);

        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                message
        );
    }
}
