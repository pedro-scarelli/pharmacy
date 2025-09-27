package com.furb.pharmacy.invoice.server;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PharmacyInvoiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PharmacyInvoiceApplication.class, args);
	}
}
