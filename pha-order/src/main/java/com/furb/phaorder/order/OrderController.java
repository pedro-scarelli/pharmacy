package com.furb.phaorder.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<Map<String, String>> criarPedido(@RequestBody Order order) {
        orderService.createOrder(order);

        return new ResponseEntity<>(Map.of("pedidoId", order.id().toString()), HttpStatus.CREATED);
    }
}
