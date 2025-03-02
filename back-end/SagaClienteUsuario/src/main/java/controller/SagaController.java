package controller;

import java.util.Map;
import consumer.ConsumerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constantes.RabbitmqConstantes;
import dto.ClientesDTO;
import dto.UserCliente;
import service.SagaService;

@RestController
@RequestMapping("/sagaClienteUsuario")
public class SagaController {

	@Autowired
	private SagaService sagaService;
	
    @PostMapping
    private ResponseEntity<Object> cadastra(@RequestBody UserCliente user) {
        try {
        	ClientesDTO get = user.getClienteDTO();
        	get.setTipo("CLIENTE");
        	System.out.print(user.toString());
            sagaService.enviaMensagem(RabbitmqConstantes.FILA_CLIENTE, user);

            return ResponseEntity.ok("Requisição de cadastro na fila");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

