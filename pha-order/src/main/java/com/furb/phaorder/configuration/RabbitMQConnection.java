package com.furb.phaorder.configuration;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnection {

    public static final String QUEUE_PEDIDO = "resposta_estoque";

    public static final String PHARMACY_EXCHANGE = "farmacia";

    public static final String VERIFY_STOCK_ROUTING_KEY = "verificar_estoque";

    public static final String PROCESS_PAYMENT_ROUTING_KEY = "pagamento.solicitado";

    private final ConnectionFactory factory;


    public RabbitMQConnection(RabbitMQConfiguration rabbitMqConfiguration) {
        factory = new ConnectionFactory();
        factory.setHost(rabbitMqConfiguration.host());
        factory.setPort(rabbitMqConfiguration.port());
        factory.setUsername(rabbitMqConfiguration.user());
        factory.setPassword(rabbitMqConfiguration.password());

        instantiateConnection();
    }

    private void instantiateConnection() {
        try (Connection connection = factory.newConnection()) {
            var channel = connection.createChannel();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create RabbitMQ connection or channel", e);
        }
    }
}
