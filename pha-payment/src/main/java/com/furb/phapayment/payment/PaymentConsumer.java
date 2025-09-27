package com.furb.phapayment.payment;

import com.furb.phapayment.configuration.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConnection.PAYMENT_QUEUE)
    public void receberMensagem(Message message) {
        var orderId = objectMapper.readValue(message.getBody(), UUID.class);
        log.info("Recebida mensagem para pagamento: {}", orderId);
        paymentService.processPayment(orderId);
    }
}