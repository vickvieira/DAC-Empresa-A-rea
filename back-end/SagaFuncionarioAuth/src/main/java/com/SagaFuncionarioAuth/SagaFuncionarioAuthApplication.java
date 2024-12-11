package com.SagaFuncionarioAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"connections", "constantes","controller","dto","service","consumer"})
public class SagaFuncionarioAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagaFuncionarioAuthApplication.class, args);
	}

}
