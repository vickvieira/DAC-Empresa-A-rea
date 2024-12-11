package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constantes.RabbitmqConstantes;
import dto.FuncionarioDTO;
import dto.UserCliente;
import service.SagaService;

@RestController
@RequestMapping("/sagaFuncionario")
public class SagaController {

	@Autowired
	private SagaService sagaService;
	
    @PostMapping
    private ResponseEntity<Object> cadastra(@RequestBody UserCliente user) {
        try {
        	FuncionarioDTO get = user.getClienteDTO();
        	get.setTipo("FUNCIONARIO");
        	System.out.print(user.toString());
            sagaService.enviaMensagem(RabbitmqConstantes.FILA_CADASTRO_FUNC, user);

            return ResponseEntity.ok("Requisição de cadastro na fila");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

