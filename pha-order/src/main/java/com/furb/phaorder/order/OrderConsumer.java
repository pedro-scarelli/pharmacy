package com.furb.phaorder.order;

import com.furb.phaorder.config.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConnection.QUEUE_PEDIDO)
    public void receberMensagem(Message message) {
        StockResponseMessage stockResponseMessage = objectMapper.readValue(message.getBody(), StockResponseMessage.class);
        log.info("Recebida mensagem: {}", stockResponseMessage.getOrderId());
        orderService.processStockResponse(stockResponseMessage);
    }
}