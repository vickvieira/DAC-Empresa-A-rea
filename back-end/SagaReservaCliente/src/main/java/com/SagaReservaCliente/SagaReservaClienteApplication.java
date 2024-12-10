package com.SagaReservaCliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"connections", "constantes","controller","dto","service","consumer"})
@EntityScan(basePackages = "dto")
public class SagaReservaClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagaReservaClienteApplication.class, args);
	}

}
