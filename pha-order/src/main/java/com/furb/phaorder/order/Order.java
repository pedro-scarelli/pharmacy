package com.furb.phaorder.order;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public class Order {
    
    @NonNull
    private UUID id;
    
    @NonNull
    private String product;

    private int quantity;

    private double price;
    
}
