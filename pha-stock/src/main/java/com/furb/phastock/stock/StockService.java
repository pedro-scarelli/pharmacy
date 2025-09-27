package com.furb.phastock.stock;

import com.furb.phastock.config.CustomMessageSender;
import org.springframework.stereotype.Service;

import com.furb.phastock.config.RabbitMQConnection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final CustomMessageSender customMessageSender;

    public void processarVerificacaoEstoque(PedidoMessage message) {
        log.info("Processando verificação de estoque para pedido: {}", message.getPedidoId());

        int chance = (int) (Math.random() * 100);
        if (chance < 60) {
            log.info("Estoque disponível para o pedido {}.", message.getPedidoId());
            message.setSucesso(true);
            message.setMensagem("ok");
        } else {
            log.info("Estoque INDISPONÍVEL para o pedido {}.", message.getPedidoId());
            message.setSucesso(false);
            message.setMensagem("Quantidade indisponível");
        }

        customMessageSender.sendMessage(
                message,
                RabbitMQConnection.EXCHANGE_PHARMACY,
                RabbitMQConnection.RK_RESPOSTA_ESTOQUE
        );

        log.info("Resposta de estoque enviada para pedido: {}", message.getPedidoId());
    }
}