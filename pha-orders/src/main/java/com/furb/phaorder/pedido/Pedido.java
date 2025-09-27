package com.furb.phaorder.pedido;

import java.util.UUID;

import lombok.NonNull;

public class Pedido {
    
    @NonNull
    UUID id;
    
    @NonNull
    String produto;

    int quantidade;

    double preco;
    
}
