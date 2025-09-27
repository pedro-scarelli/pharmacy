package com.furb.phastock.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class OrderMessage {

    private UUID orderId;
    private String product;
    private Integer quantity;

}
