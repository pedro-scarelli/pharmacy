package com.furb.pharmacy.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceService {
    private static final String MARKER = "=".repeat(80);

    // NOTE only printing invoice info for demo purposes
    public void process(InvoiceMessage message) {
        var text = buildInvoiceText(message);
        log.info(text);
    }

    private static String buildInvoiceText(InvoiceMessage message) {
        var amount = String.format("%.2f %s", message.amount(), message.currency());
        return String.join(
            System.lineSeparator(),
            MARKER,
            buildLine("CUSTOMER", message.customer().id()),
            buildLine("ORDER", message.orderId()),
            buildLine("TOTAL", amount),
            buildLine("PAYMENT METHOD", message.paymentMethod()),
            MARKER
        );
    }

    private static String buildLine(String label, String value) {
        return String.format("%16s %16s", label, value);
    }
}
