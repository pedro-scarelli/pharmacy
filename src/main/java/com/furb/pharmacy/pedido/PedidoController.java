package com.furb.pharmacy.pedido;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public String criarPedido(@RequestBody Pedido pedido) {
        pedidoService.criarPedido(pedido);
        return "Pedido criado: " + pedido.id;
    }
}
