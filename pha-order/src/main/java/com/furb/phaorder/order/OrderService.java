package com.furb.phaorder.order;

import com.furb.phaorder.configuration.CustomMessageSender;
import com.furb.phaorder.configuration.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final Map<UUID, Order> orderStore = new Hashtable<>(16);
    private final CustomMessageSender customMessageSender;

    public boolean exists(UUID orderId) {
        return orderStore.containsKey(orderId);
    }

    public void saveOrder(Order order) {
        log.info("Criando pedido: {}", order.id());
        orderStore.put(order.id(), order);
        sendOrderToInventory(order);
    }

    private void sendOrderToInventory(Order order) {
        var message = new OrderMessage(
            order.id(),
            order.product(),
            order.quantity()
        );

        log.info("Enviando para verificação de estoque: {}", order.id());
        customMessageSender.sendMessage(
            message,
            RabbitMQConnection.PHARMACY_EXCHANGE,
            RabbitMQConnection.VERIFY_STOCK_ROUTING_KEY
        );
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
        var order = orderStore.get(orderId);
        customMessageSender.sendMessage(
            OrderConverter.toPayment(order),
            RabbitMQConnection.PHARMACY_EXCHANGE,
            RabbitMQConnection.PROCESS_PAYMENT_ROUTING_KEY
        );
        
        log.info("Enviado para processamento de pagamento: {}", orderId);
    }
}