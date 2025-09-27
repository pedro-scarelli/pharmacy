package com.furb.pharmacy.invoice;

import com.furb.pharmacy.customer.CustomerData;
import lombok.NonNull;

import java.time.LocalDate;

public record InvoiceMessage(
    @NonNull
    String orderId,
    double amount,
    String currency,
    String paymentMethod,
    LocalDate paymentDate,
    @NonNull
    CustomerData customer
) {}
