package com.furb.phaorder.pedido;

import java.io.Serializable;

import lombok.Data;

@Data
public class PedidoMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pedidoId;
    private String acao;
    private int quantidade;
    private boolean sucesso;
    private String mensagem;
}