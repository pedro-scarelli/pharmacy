package com.furb.phastock.stock;

import com.furb.phastock.config.CustomMessageSender;
import com.furb.phastock.config.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final CustomMessageSender customMessageSender;

    public void verifyIfItemQuantityIsAvailableOnStock(OrderMessage orderMessage) {
        log.info("Processando verificação de estoque para pedido: {}", orderMessage.getOrderId());

        var isQuantityAvailable = verifyStockAvailability(orderMessage);
        customMessageSender.sendMessage(
                new StockResponseMessage(orderMessage.getOrderId(), isQuantityAvailable),
                RabbitMQConnection.PHARMACY_EXCHANGE,
                RabbitMQConnection.STOCK_RESPONSE_ROUTING_KEY
        );

        log.info("Resposta de estoque enviada para pedido: {}", orderMessage.getOrderId());
    }

    private boolean verifyStockAvailability(OrderMessage orderMessage) {
        int chance = (int) (Math.random() * 100);
        if (chance < 60) {
            log.info("Estoque disponível para o pedido {}.", orderMessage.getOrderId());
            return true;
        }

        log.info("Estoque INDISPONÍVEL para o pedido {}.", orderMessage.getOrderId());
        return false;
    }
}