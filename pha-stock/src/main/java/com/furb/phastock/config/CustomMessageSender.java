package com.furb.phastock.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMessageSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Object object, String exchange, String routingKey) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message message = MessageBuilder.withBody(object.toString().getBytes())
                                       .andProperties(messageProperties)
                                       .build();
        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                message
        );
    }
}
