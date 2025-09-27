package com.furb.phapayment.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnection {

    public static final String PAYMENT_QUEUE = "pagamento";

    public static final String PHARMACY_EXCHANGE = "farmacia";

    public static final String PAYMENT_RESPONSE_ROUTING_KEY = "pagamento.processado";

    private final ConnectionFactory factory;


    public RabbitMQConnection(RabbitMQConfig rabbitMqConfig) {
        factory = new ConnectionFactory();
        factory.setHost(rabbitMqConfig.host());
        factory.setPort(rabbitMqConfig.port());
        factory.setUsername(rabbitMqConfig.user());
        factory.setPassword(rabbitMqConfig.password());

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
