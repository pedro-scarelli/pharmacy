package com.furb.phaorder.order;

import com.furb.phaorder.config.CustomMessageSender;
import com.furb.phaorder.config.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final CustomMessageSender customMessageSender;


    private final ObjectMapper objectMapper;

    public void createOrder(Order order) {
        log.info("Criando pedido: {}", order.getId());
        
        verifyStock(order);
    }

    private void verifyStock(Order order) {
        var message = new OrderMessage(
            order.getId(),
            1,
            1
        );

        customMessageSender.sendMessage(
            message,
            RabbitMQConnection.PHARMACY_EXCHANGE,
            RabbitMQConnection.VERIFY_STOCK_ROUTING_KEY
        );

        log.info("Enviado para verificação de estoque: {}", order.getId());
    }

    public void processStockResponse(StockResponseMessage message) {
        var orderId = message.getOrderId();
        log.info("Processando resposta do estoque para pedido: {}", orderId);
        
        if (message.getIsAvailable()) {
            processPayment(orderId);
            return;
        }

        log.warn("Estoque indisponível para pedido: {}", orderId);
    }

    private void processPayment(UUID orderId) {
        customMessageSender.sendMessage(
            orderId,
            RabbitMQConnection.PHARMACY_EXCHANGE,
            RabbitMQConnection.PROCESS_PAYMENT_ROUTING_KEY
        );
        
        log.info("Enviado para processamento de pagamento: {}", orderId);
    }
}