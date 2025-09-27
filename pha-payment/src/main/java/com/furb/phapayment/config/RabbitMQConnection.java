package com.furb.phapayment.config;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQConnection {

    public static final String QUEUE_PAYMENT = "pagamento";

    public static final String EXCHANGE_PHARMACY = "farmacia";

    public static final String RK_RESPOSTA_PAGAMENTO = "pagamento.processado";

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
