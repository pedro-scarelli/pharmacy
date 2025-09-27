package com.furb.phapayment.payment;

import com.furb.phapayment.configuration.CustomMessageSender;
import com.furb.phapayment.configuration.RabbitMQConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final Map<UUID, Payment> paymentStore = new Hashtable<>(16);
    private final CustomMessageSender customMessageSender;

    private double userBalance = 1000.0D;

    public void processPayment(Payment payment) {
        if (!savePayment(payment)) {
            return;
        }

        log.info("Enviando pagamento para emissão de NF: {}", payment.getOrderId());
        var invoicePayload = PaymentConverter.from(payment);
        customMessageSender.sendMessage(
                invoicePayload,
                RabbitMQConnection.PHARMACY_EXCHANGE,
                RabbitMQConnection.PAYMENT_RESPONSE_ROUTING_KEY
        );
    }

    private boolean savePayment(Payment payment) {
        var ok =  true;
        var amount = payment.getAmount();
        if (userBalance < amount || paymentStore.containsKey(payment.getId())) {
            payment.setStatus(PaymentStatus.ERROR);
            ok = false;
        } else {
            userBalance -= amount;
            payment.setStatus(PaymentStatus.SUCCESS);
        }

        log.info(
            "Salvando pagamento (pedido: {}, valor: {}, status: {})",
            payment.getOrderId(),
            payment.getAmount(),
            payment.getStatus()
        );

        paymentStore.put(payment.getId(), payment);
        return ok;
    }
}
