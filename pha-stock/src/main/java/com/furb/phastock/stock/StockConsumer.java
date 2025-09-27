package com.furb.phastock.stock;

import com.furb.phastock.config.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockConsumer {

    private final StockService stockService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConnection.STOCK_QUEUE)
    public void receberMensagem(Message message) {
        OrderMessage orderMessage = objectMapper.readValue(message.getBody(), OrderMessage.class);
        log.info("Recebida mensagem: {}", orderMessage.getOrderId());
        stockService.verifyIfItemQuantityIsAvailableOnStock(orderMessage);
    }
}