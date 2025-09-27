package com.furb.phapayment.payment;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@Slf4j
public class Payment {

    @NonNull
    UUID id;

    @NonNull
    UUID orderId;

    @NonNull
    PaymentStatus status;

    double preco;

    LocalDateTime timestamp;

    String type;


    public Payment(UUID orderId, PaymentStatus status, double price, LocalDateTime timestamp, String type) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.status = status;
        this.preco = price;
        this.timestamp = timestamp;
        this.type = type;
        simulatePayment();
    }

    private void simulatePayment() {
        var random = new Random();
        int chance = random.nextInt(100);
        if (chance < 70) {
            log.info("Pagamento processado com sucesso para o pedido: {}", orderId);
            status = PaymentStatus.SUCCESS;
            return;
        }

        log.info("Pagamento falhou para o pedido: {}", orderId);
        status = PaymentStatus.ERROR;
    }
}
