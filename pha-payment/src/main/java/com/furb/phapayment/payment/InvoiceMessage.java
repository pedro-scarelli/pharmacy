package com.furb.phapayment.payment;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record InvoiceMessage(
    @NonNull
    UUID orderId,
    Double amount,
    String currency,
    String paymentMethod,
    LocalDateTime paymentDate,
    @NonNull
    CustomerData customer
) {}
