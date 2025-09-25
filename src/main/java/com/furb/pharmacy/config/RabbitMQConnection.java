package com.furb.pharmacy.config;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQConnection {

    public static final String QUEUE_ESTOQUE = "estoque.queue";
    public static final String QUEUE_PAGAMENTO = "pagamento.queue";
    public static final String QUEUE_PEDIDO = "pedido.queue";
    public static final String QUEUE_NOTA_FISCAL = "notafiscal.queue";

    public static final String QUEUE_PAYMENT = "payment";
    public static final String EXCHANGE_PHARMACY = "pharmacy.exchange";

    public static final String RK_VERIFICAR_ESTOQUE = "verificar.estoque";
    public static final String RK_PROCESSAR_PAGAMENTO = "processar.pagamento";
    public static final String RK_RESPOSTA_ESTOQUE = "resposta.estoque";
    public static final String RK_RESPOSTA_PAGAMENTO = "resposta.pagamento";
    public static final String RK_GERAR_NF = "gerar.nf";

    private final ConnectionFactory factory;

    private Connection connection;

    private Channel channel;


    public RabbitMQConnection(RabbitMQConfig rabbitMqConfig) {
        factory = new ConnectionFactory();
        factory.setHost(rabbitMqConfig.host());
        factory.setPort(rabbitMqConfig.port());
        factory.setUsername(rabbitMqConfig.user());
        factory.setPassword(rabbitMqConfig.password());

        instantiateConnection();
    }

    private void instantiateConnection() {
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create RabbitMQ connection or channel", e);
        }
    }
}
