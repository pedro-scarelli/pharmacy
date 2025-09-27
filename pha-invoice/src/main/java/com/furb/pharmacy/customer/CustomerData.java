package com.furb.pharmacy.customer;

import lombok.NonNull;

public record CustomerData(
    @NonNull
    String id,
    @NonNull
    String name,
    @NonNull
    String email
) {}
