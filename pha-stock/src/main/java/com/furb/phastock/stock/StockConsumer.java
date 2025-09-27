package com.furb.phastock.stock;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.furb.phastock.config.RabbitMQConnection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockConsumer {

    private final StockService stockService;

    @RabbitListener(queues = RabbitMQConnection.STOCK_QUEUE)
    public void receberMensagem(PedidoMessage message) {
        log.info("Recebida mensagem: {} - Ação: {}", message.getPedidoId(), message.getAcao());

        stockService.processarVerificacaoEstoque(message);
    }
}