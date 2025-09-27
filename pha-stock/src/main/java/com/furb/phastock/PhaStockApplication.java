package com.furb.phastock;

import com.furb.phastock.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableRabbit
@EnableConfigurationProperties(value = {RabbitMQConfig.class})
public class PhaStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhaStockApplication.class, args);
	}

}
