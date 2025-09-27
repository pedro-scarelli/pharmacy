package com.furb.pharmacy.configuration;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.rabbitmq")
public record RabbitMQConfiguration(
    @NonNull
    String host,
    int port,
    @NonNull
    String user,
    @NonNull
    String password
) {}
