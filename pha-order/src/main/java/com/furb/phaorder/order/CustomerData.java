package com.furb.phaorder.order;

import lombok.NonNull;

public record CustomerData(
    @NonNull
    String id,
    @NonNull
    String name,
    @NonNull
    String email
) {}
