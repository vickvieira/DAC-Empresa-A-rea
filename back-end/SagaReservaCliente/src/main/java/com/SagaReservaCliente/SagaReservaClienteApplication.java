package com.SagaReservaCliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"connections", "constantes","controller","dto","service","consumer"})
public class SagaReservaClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagaReservaClienteApplication.class, args);
	}

}
