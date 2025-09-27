package com.furb.phastock.stock;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Stock {

    private final Map<String, Integer> productsStock = new HashMap<>();

    public Stock() {
        productsStock.put("dipirona", 10);
        productsStock.put("paracetamol", 5);
        productsStock.put("ibuprofeno", 0);
    }

    public boolean verifyStockAvailability(String product, int quantity) {
        var availableQuantity = productsStock.getOrDefault(product, 0);
        var availableQuantityMinusOrderQuantity = availableQuantity - quantity;
        System.out.println("Available quantity of " + product + ": " + availableQuantity);
        System.out.println("Order quantity of " + product + ": " + quantity);
        System.out.println("Available quantity minus order quantity of " + product + ": " + availableQuantityMinusOrderQuantity);
        if (availableQuantityMinusOrderQuantity >= 0) {
            productsStock.put(product, availableQuantityMinusOrderQuantity);
            return true;
        }

        return false;
    }
}
