package com.SagaClienteUsuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"SagaController", "SagaService", "DTO"})
public class SagaClienteUsuarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(SagaClienteUsuarioApplication.class, args);
    }
}