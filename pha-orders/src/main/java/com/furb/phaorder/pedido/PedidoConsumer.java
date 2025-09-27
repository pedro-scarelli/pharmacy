package com.furb.phaorder.pedido;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.furb.phaorder.config.RabbitMQConnection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoConsumer {

    private final PedidoService pedidoService;

    @RabbitListener(queues = RabbitMQConnection.QUEUE_PEDIDO)
    public void receberMensagem(PedidoMessage message) {
        log.info("Recebida mensagem: {} - Ação: {}", message.getPedidoId(), message.getAcao());
        pedidoService.processarRespostaEstoque(message);
    }
}