package com.furb.pharmacy.invoice;

import com.furb.pharmacy.customer.CustomerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class InvoiceService {
    private static final String MARKER = "=".repeat(80);

    // NOTE only printing invoice info for demo purposes
    public void process(UUID orderId) {
        var invoice = getInvoiceInfo(orderId);
        var text = buildInvoiceText(invoice);
        log.info(text);
    }

    private static InvoiceMessage getInvoiceInfo(UUID orderId) {
        return new InvoiceMessage(
                orderId,
                199.90,
                "USD",
                "CREDIT_CARD",
                LocalDate.now(),
                new CustomerData("", "", "")
        );
    }

    private static String buildInvoiceText(InvoiceMessage message) {
        var amount = String.format("%.2f %s", message.amount(), message.currency());
        return String.join(
            System.lineSeparator(),
            MARKER,
            buildLine("CUSTOMER", message.customer().id()),
            buildLine("ORDER", message.orderId().toString()),
            buildLine("TOTAL", amount),
            buildLine("PAYMENT METHOD", message.paymentMethod()),
            MARKER
        );
    }

    private static String buildLine(String label, String value) {
        return String.format("%16s %16s", label, value);
    }
}
