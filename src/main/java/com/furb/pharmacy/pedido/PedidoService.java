package com.furb.pharmacy.pedido;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.furb.pharmacy.config.RabbitMQConnection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final RabbitTemplate rabbitTemplate;

    public void criarPedido(Pedido pedido) {
        // 1. Gerar ID e definir status inicial
        pedido.id = UUID.randomUUID();
        
        log.info("Criando pedido: {}", pedido.id);
        
        // 2. Verificar estoque7
        verificarEstoque(pedido);
    }

    private void verificarEstoque(Pedido pedido) {
        PedidoMessage message = new PedidoMessage();
        message.setPedidoId(pedido.id.toString());
        message.setAcao("VERIFICAR_ESTOQUE");
        message.setDados(pedido);
        
        rabbitTemplate.convertAndSend(
            RabbitMQConnection.EXCHANGE_PHARMACY,
            RabbitMQConnection.RK_VERIFICAR_ESTOQUE,
            message
        );
        
        log.info("Enviado para verificação de estoque: {}", pedido.id);
    }

    public void processarRespostaEstoque(PedidoMessage message) {
        log.info("Processando resposta do estoque para pedido: {}", message.getPedidoId());
        
        if (message.isSucesso()) {
            // Estoque OK, processar pagamento
            processarPagamento(message);
        } else {
            log.warn("Estoque indisponível para pedido: {}", message.getPedidoId());
        }
    }

    private void processarPagamento(PedidoMessage message) {
        message.setAcao("PROCESSAR_PAGAMENTO");
        
        rabbitTemplate.convertAndSend(
            RabbitMQConnection.EXCHANGE_PHARMACY,
            RabbitMQConnection.RK_PROCESSAR_PAGAMENTO,
            message
        );
        
        log.info("Enviado para processamento de pagamento: {}", message.getPedidoId());
    }

    public void processarRespostaPagamento(PedidoMessage message) {
        log.info("Processando resposta do pagamento para pedido: {}", message.getPedidoId());
        
        if (message.isSucesso()) {
            // Pagamento OK, gerar nota fiscal
            //gerarNotaFiscal(message);
        } else {
            log.warn("Pagamento rejeitado para pedido: {}", message.getPedidoId());
        }
    }

    /*private void gerarNotaFiscal(PedidoMessage message) {
        message.setAcao("GERAR_NF");
        
        rabbitTemplate.convertAndSend(
            RabbitMQConnection.EXCHANGE_PHARMACY,
            RabbitMQConnection.RK_GERAR_NF,
            message
        );
        
        log.info("Enviado para geração de NF: {}", message.getPedidoId());
    } */
}