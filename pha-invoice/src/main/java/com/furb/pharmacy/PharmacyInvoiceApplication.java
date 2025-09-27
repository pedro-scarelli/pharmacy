package com.furb.pharmacy;

import com.furb.pharmacy.configuration.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableRabbit
@EnableConfigurationProperties(value = {RabbitMQConfiguration.class})
public class PharmacyInvoiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PharmacyInvoiceApplication.class, args);
	}
}
