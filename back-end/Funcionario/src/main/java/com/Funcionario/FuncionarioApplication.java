package com.Funcionario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"connections", "constantes","controller","dto","service","consumer"})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "dto")
public class FuncionarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuncionarioApplication.class, args);
	}

}
