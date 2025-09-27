package com.furb.phapayment.pagamento;

import com.furb.phapayment.config.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagamentoConsumer {

    private final PagamentoService pagamentoService;

    @RabbitListener(queues = RabbitMQConnection.QUEUE_PAYMENT)
    public void receberMensagem(PagamentoMessage message) {
        log.info("Recebida mensagem para pagamento: {}", message.getPedidoId());
        pagamentoService.processarPagamento(message);

        log.error("Falha ao processar pagamento para o pedido: {}", message.getPedidoId());
    }
}