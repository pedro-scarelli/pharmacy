package com.furb.phastock.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class OrderMessage {

    private UUID orderId;
    private Integer itemNumber;
    private Integer quantity;

}
