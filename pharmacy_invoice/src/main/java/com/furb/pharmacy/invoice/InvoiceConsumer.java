package com.furb.pharmacy.invoice;

import com.furb.pharmacy.configuration.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceConsumer {
    private final InvoiceService service;

    @RabbitListener(queues = RabbitMQConnection.QUEUE_ORDER)
    public void consume(InvoiceMessage message) {
        log.info("Generating invoice from order {}", message.orderId());

        InvoiceValidator.validate(message);
        service.process(message);
    }
}
