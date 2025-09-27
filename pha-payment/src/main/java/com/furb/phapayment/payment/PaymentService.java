package com.furb.phapayment.payment;

import com.furb.phapayment.config.CustomMessageSender;
import com.furb.phapayment.config.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final CustomMessageSender customMessageSender;

    public void processPayment(UUID orderId) {
        var orderValue = getOrderValue(orderId);
        new Payment(
            orderId,
            PaymentStatus.PROCESSING,
            orderValue,
            LocalDateTime.now(),
            "CARD"
        );
        savePayment(orderId, orderValue);

        customMessageSender.sendMessage(
                orderId,
                RabbitMQConnection.PHARMACY_EXCHANGE,
                RabbitMQConnection.PAYMENT_RESPONSE_ROUTING_KEY
        );
        
        log.info("Enviado para emissão de NF: {}", orderId);
    }

    private double getOrderValue(UUID orderId) {
        return 100d;
    }

    private void savePayment(UUID orderId, double orderValue) {
        log.info("Salvando pagamento do pedido: {} com valor: {}", orderId.toString(), orderValue);
    }
}