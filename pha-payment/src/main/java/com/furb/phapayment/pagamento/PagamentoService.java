package com.furb.phapayment.pagamento;

import com.furb.phapayment.config.CustomMessageSender;
import com.furb.phapayment.config.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagamentoService {

    private final CustomMessageSender customMessageSender;

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

        customMessageSender.sendMessage(
                message,
                RabbitMQConnection.EXCHANGE_PHARMACY,
                RabbitMQConnection.RK_RESPOSTA_PAGAMENTO
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