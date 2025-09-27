package com.furb.phaorder.order;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class OrderConverter {
    public static Payment toPayment(Order order) {
        return new Payment(
            UUID.randomUUID(),
            order.id(),
            order.price() * order.quantity(),
            paymentCurrencyStub(),
            LocalDateTime.now(),
            paymentMethodStub(),
            customerDataStub()
        );
    }

    // NOTE using static stubs not to over-complicate data flow for the POC
    private static String paymentCurrencyStub() {
        return "BRL";
    }

    private static String paymentMethodStub() {
        return "CREDIT";
    }

    private static CustomerData customerDataStub() {
        return new CustomerData(
            "0ed864c1-c959-4520-b4e3-00402abdc479",
            "Smitty Werbenjägermanjensen",
            "smittyw@mail.com"
        );
    }
}
