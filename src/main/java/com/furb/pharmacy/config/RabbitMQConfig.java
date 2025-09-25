package com.furb.pharmacy.config;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.rabbitmq")
public record RabbitMQConfig(

        @NonNull
        String host,

        int port,

        @NonNull
        String user,

        @NonNull
        String password

) {}
