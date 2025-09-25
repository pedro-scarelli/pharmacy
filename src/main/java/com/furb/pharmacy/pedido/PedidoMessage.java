package com.furb.pharmacy.pedido;

import lombok.Data;

@Data
public class PedidoMessage {
    private String pedidoId;
    private String acao;
    private Object dados;
    private boolean sucesso;
    private String mensagem;
}