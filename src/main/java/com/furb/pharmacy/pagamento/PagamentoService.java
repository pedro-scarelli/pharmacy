package com.furb.pharmacy.pagamento;

import com.furb.pharmacy.config.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagamentoService {

    private final RabbitTemplate rabbitTemplate;

    public void processarPagamento(PagamentoMessage message) {
        var valorPedido = getValorDoPedido(message.getPedidoId());
        new Pagamento(
            message.getPedidoId(),
            PagamentoStatus.PROCESSANDO,
            valorPedido,
            LocalDateTime.now(),
            "CARTAO"
        );
        salvarPagamento(message, valorPedido);

        rabbitTemplate.convertAndSend(
            RabbitMQConnection.EXCHANGE_PHARMACY,
            RabbitMQConnection.RK_RESPOSTA_PAGAMENTO,
            message
        );
        
        log.info("Enviado para pagamento processado: {}", message.getPedidoId());
    }

    private double getValorDoPedido(String pedidoId) {
        return 100d;
    }

    private void salvarPagamento(PagamentoMessage message, double valorPedido) {
        log.info("Salvando pagamento do pedido: {} com valor: {}", message.getPedidoId(), valorPedido);
    }
}