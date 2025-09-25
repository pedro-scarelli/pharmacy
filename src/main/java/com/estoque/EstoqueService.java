package com.estoque; 

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EstoqueService {

    private final RabbitTemplate rabbitTemplate;

    private static final Map<String, Integer> estoque = new HashMap<>();

    static {
        estoque.put("dipirona", 10);
        estoque.put("paracetamol", 5);
        estoque.put("ibuprofeno", 0);
    }

    public EstoqueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "estoque_queue")
    public void processarPedido(String mensagem, org.springframework.amqp.core.Message message) {
        System.out.println("[ESTOQUE] Pedido recebido: " + mensagem);

        // parse simples do JSON
        String produto = "";
        int quantidade = 1;
        try {
            String[] partes = mensagem.replace("{", "")
                                      .replace("}", "")
                                      .replace("\"", "")
                                      .split(",");
            for (String parte : partes) {
                if (parte.contains("produto")) {
                    produto = parte.split(":")[1].trim();
                } else if (parte.contains("quantidade")) {
                    quantidade = Integer.parseInt(parte.split(":")[1].trim());
                }
            }
        } catch (Exception e) {
            System.err.println("[ERRO] Não foi possível interpretar o pedido: " + e.getMessage());
        }

        // regra de estoque
        String resposta;
        if (estoque.containsKey(produto) && estoque.get(produto) >= quantidade) {
            estoque.put(produto, estoque.get(produto) - quantidade);
            resposta = "{ \"status\": \"OK\", \"mensagem\": \"" + quantidade + "x " + produto + " reservado.\" }";
        } else {
            resposta = "{ \"status\": \"FALHA\", \"mensagem\": \"Produto " + produto + " indisponível.\" }";
        }

        // envia a resposta usando replyTo
        String replyTo = message.getMessageProperties().getReplyTo();
        String correlationId = message.getMessageProperties().getCorrelationId();

        if (replyTo != null) {
            MessageProperties props = new MessageProperties();
            props.setCorrelationId(correlationId);
            Message respostaMsg = new Message(resposta.getBytes(StandardCharsets.UTF_8), props);
            rabbitTemplate.send(replyTo, respostaMsg);
            System.out.println("[ESTOQUE] Resposta enviada: " + resposta);
        } else {
            System.err.println("[ESTOQUE] Não foi possível responder: replyTo nulo");
        }
    }
}
