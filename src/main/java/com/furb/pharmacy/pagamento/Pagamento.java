package com.furb.pharmacy.pagamento;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@Slf4j
public class Pagamento {

    @NonNull
    UUID id;

    @NonNull
    String pedidoId;

    @NonNull
    PagamentoStatus status;

    double preco;

    LocalDateTime timestamp;

    String tipo;


    public Pagamento(String pedidoId, PagamentoStatus status, double preco, LocalDateTime timestamp, String tipo) {
        this.id = UUID.randomUUID();
        this.pedidoId = pedidoId;
        this.status = status;
        this.preco = preco;
        this.timestamp = timestamp;
        this.tipo = tipo;
        simulatePayment();
    }

    private void simulatePayment() {
        var random = new Random();
        int chance = random.nextInt(100);
        if (chance < 70) {
            log.info("Pagamento processado com sucesso para o pedido: {}", pedidoId);
            status = PagamentoStatus.SUCESSO;
            return;
        }

        log.info("Pagamento falhou para o pedido: {}", pedidoId);
        status = PagamentoStatus.ERRO;
    }
}
