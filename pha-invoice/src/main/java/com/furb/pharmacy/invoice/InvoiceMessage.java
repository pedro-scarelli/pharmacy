package com.furb.pharmacy.invoice;

import com.furb.pharmacy.customer.CustomerData;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record InvoiceMessage(
    @NonNull
    UUID orderId,
    double amount,
    String currency,
    String paymentMethod,
    LocalDateTime paymentDate,
    @NonNull
    CustomerData customer
) {}
