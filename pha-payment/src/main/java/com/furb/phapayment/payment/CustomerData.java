package com.furb.phapayment.payment;

import lombok.NonNull;

public record CustomerData(
    @NonNull
    String id,
    @NonNull
    String name,
    @NonNull
    String email
) {}
