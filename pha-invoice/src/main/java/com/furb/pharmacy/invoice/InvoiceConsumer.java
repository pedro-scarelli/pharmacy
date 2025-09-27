package com.furb.pharmacy.invoice;

import com.furb.pharmacy.configuration.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceConsumer {
    private final InvoiceService service;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConnection.QUEUE_ORDER)
    public void consume(Message message) {
        var invoiceMessage = objectMapper.readValue(message.getBody(), UUID.class);
        log.info("Generating invoice from order {}", invoiceMessage);
        service.process(invoiceMessage);
    }
}
