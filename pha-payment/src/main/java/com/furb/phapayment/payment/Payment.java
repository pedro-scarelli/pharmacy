package com.furb.phapayment.payment;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Slf4j
public class Payment {
    private final UUID id;
    private final UUID orderId;
    private final double amount;
    private final String currency;
    private final LocalDateTime date;
    private final String method;
    private final CustomerData customerData;

    private PaymentStatus status;
}
