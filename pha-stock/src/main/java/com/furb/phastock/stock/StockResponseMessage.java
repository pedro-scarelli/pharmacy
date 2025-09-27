package com.furb.phastock.stock;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockResponseMessage {

    private UUID orderId;
    private Boolean isAvailable;

    public StockResponseMessage(UUID orderId, Boolean isAvailable) {
        this.orderId = orderId;
        this.isAvailable = isAvailable;
    }
}
