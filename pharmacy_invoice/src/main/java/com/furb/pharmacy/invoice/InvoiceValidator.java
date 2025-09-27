package com.furb.pharmacy.invoice;

import java.util.Set;

public final class InvoiceValidator {
    private static final Set<String> VALID_CURRENCIES = Set.of(
        "BRL",
        "USD",
        "GBP"
    );
    private static final Set<String> VALID_PAYMENT_METHODS = Set.of(
        "CREDIT",
        "DEBIT",
        "CASH"
    );

    public static void validate(InvoiceMessage message) {
        if (message.amount() < 0.0D) {
            throw new IllegalArgumentException("");
        }

        validateField(message.currency(), "currency", VALID_CURRENCIES);
        validateField(message.paymentMethod(), "payment method", VALID_PAYMENT_METHODS);
    }

    private static void validateField(String field, String name, Set<String> set) {
        if (!set.contains(field)) {
            var message = String.format("invalid %s '%s'", field, name);
            throw new IllegalArgumentException(message);
        }
    }
}
