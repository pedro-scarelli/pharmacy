package com.furb.phastock.stock;

import com.furb.phastock.configuration.CustomMessageSender;
import com.furb.phastock.configuration.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final CustomMessageSender customMessageSender;

    private final Stock stock;

    public void verifyIfItemQuantityIsAvailableOnStock(OrderMessage orderMessage) {
        log.info("Processando verificação de estoque para pedido: {}", orderMessage.getOrderId());

        var isQuantityAvailable = stock.verifyStockAvailability(orderMessage.getProduct(), orderMessage.getQuantity());
        customMessageSender.sendMessage(
                new StockResponseMessage(orderMessage.getOrderId(), isQuantityAvailable),
                RabbitMQConnection.PHARMACY_EXCHANGE,
                RabbitMQConnection.STOCK_RESPONSE_ROUTING_KEY
        );

        log.info("Resposta de estoque enviada para pedido: {}", orderMessage.getOrderId());
    }

}