package com.furb.phastock;

import com.furb.phastock.configuration.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableRabbit
@EnableConfigurationProperties(value = {RabbitMQConfiguration.class})
public class PhaStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhaStockApplication.class, args);
	}

}
