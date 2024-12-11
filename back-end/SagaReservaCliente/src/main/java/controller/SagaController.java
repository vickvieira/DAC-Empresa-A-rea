package controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constantes.RabbitmqConstantes;
import dto.VooDTO;
import service.SagaService;
import models.SagaReservaRequisition;

@RestController
@RequestMapping("/sagaReservaCliente")
public class SagaController {

	@Autowired
	private SagaService sagaService;
	
    @PostMapping
    private ResponseEntity<Object> cadastraReserva(@RequestBody SagaReservaRequisition reserva) {
        try {
        	System.out.print(reserva.toString());
            sagaService.enviaMensagem(RabbitmqConstantes.FILA_VOO, reserva);
            return ResponseEntity.ok("Reserva enviada com sucesso para a fila de processamento.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping("/cancelarReserva")
    private ResponseEntity<Object> cancelaReserva(@RequestBody SagaReservaRequisition reserva) {
        try {
        	System.out.print(reserva.toString());
            sagaService.enviaMensagem(RabbitmqConstantes.VOO_CANCELA, reserva);
            return ResponseEntity.ok("Reserva enviada com sucesso para a fila de processamento.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping("/cancelarVoo")
    private ResponseEntity<Object> cancelarVoo(@RequestBody VooDTO voo) {
        try {
            System.out.print(voo.toString());
            sagaService.enviaMensagem(RabbitmqConstantes.VOO_CANCELA_VOO, voo);
            return ResponseEntity.ok("Voo enviado com sucesso para a fila de cancelamento.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping("/realizaVoo")
    private ResponseEntity<Object> realizaVoo(@RequestBody VooDTO voo) {
        try {
            System.out.print(voo.toString());
            sagaService.enviaMensagem(RabbitmqConstantes.FILA_REALIZA_VOO, voo);
            return ResponseEntity.ok("Voo enviado com sucesso para a fila de Realizcao.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
}