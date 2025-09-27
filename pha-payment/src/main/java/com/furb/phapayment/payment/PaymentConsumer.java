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
    private final ObjectMapper mapper;

    @RabbitListener(queues = RabbitMQConnection.PAYMENT_QUEUE)
    public void receberMensagem(Message message) {
        var payment = mapper.readValue(message.getBody(), Payment.class);
        log.info("Processando pagamento do pedido: {}", payment.getOrderId());
        paymentService.processPayment(payment);
    }
}