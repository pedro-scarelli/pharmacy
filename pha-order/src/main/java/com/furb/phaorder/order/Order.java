package com.furb.phaorder.order;

import lombok.NonNull;

import java.util.UUID;

public record Order(

        @NonNull
        UUID id,

        @NonNull
        String product,

        int quantity,

        double price

    ) {}
