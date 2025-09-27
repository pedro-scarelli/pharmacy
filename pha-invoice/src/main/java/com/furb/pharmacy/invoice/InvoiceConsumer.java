package com.furb.pharmacy.invoice;

import com.furb.pharmacy.configuration.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceConsumer {
    private final InvoiceService service;
    private final ObjectMapper mapper;

    @RabbitListener(queues = RabbitMQConnection.ORDER_QUEUE)
    public void consume(Message message) {
        var invoiceMessage = mapper.readValue(message.getBody(), InvoiceMessage.class);
        log.info("Generating invoice from order {}", invoiceMessage);
        service.process(invoiceMessage);
    }
}
