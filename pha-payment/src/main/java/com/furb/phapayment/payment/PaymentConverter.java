package com.furb.phapayment.payment;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentConverter {
    public static InvoiceMessage from(Payment payment) {
        return new InvoiceMessage(
            payment.getOrderId(),
            payment.getAmount(),
            payment.getCurrency(),
            payment.getMethod(),
            payment.getDate(),
            payment.getCustomerData()
        );
    }
}
