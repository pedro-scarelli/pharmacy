package com.furb.pharmacy.invoice;

import io.netty.handler.codec.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class InvoiceService {
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(1L);
    private static final int WIDTH = 36;
    private static final String MARKER = "=".repeat(1 + 2 * WIDTH);

    public void process(InvoiceMessage message) {
        // NOTE avoiding contention between loggers when printing the invoice
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            log.error("{}", e.getMessage(), e);
        }

        var invoiceText = buildInvoiceText(message);
        var output = String.format("%n%s", invoiceText);
        log.info(output);
    }

    private static String buildInvoiceText(InvoiceMessage message) {
        return String.join(
            System.lineSeparator(),
            MARKER,
            buildLine("ORDER", message.orderId().toString()),
            buildLine("CUSTOMER ID", message.customer().id()),
            buildLine("CUSTOMER NAME", message.customer().name()),
            buildLine("TOTAL", formatAmount(message)),
            buildLine("PAYMENT METHOD", message.paymentMethod()),
            buildLine("PAYMENT DATE", formatDate(message)),
            MARKER
        );
    }

    private static String buildLine(String label, String value) {
        var format = "%-" + WIDTH + "s %" + WIDTH + "s";
        return String.format(format, label, value);
    }

    private static String formatAmount(InvoiceMessage message) {
        return String.format("%.2f %s", message.amount(), message.currency());
    }

    private static String formatDate(InvoiceMessage message) {
        return DateTimeFormatter.ISO_DATE_TIME.format(message.paymentDate());
    }
}
