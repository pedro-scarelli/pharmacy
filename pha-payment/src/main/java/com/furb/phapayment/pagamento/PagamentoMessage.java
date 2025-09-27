package com.furb.phapayment.pagamento;

import lombok.Data;

import java.io.Serializable;

@Data
public class PagamentoMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String pedidoId;
}