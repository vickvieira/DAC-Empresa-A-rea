package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dto.ReservaDTO;
import models.EmbarqueReserva;
import service.ReservaService;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/realizarEmbarque")
    public ResponseEntity<String> realizarEmbarque(@RequestBody EmbarqueReserva embarqueReserva) {
        try {
            boolean isValido = reservaService.validarReservaVoo(
                embarqueReserva.getCodigoVoo(),
                embarqueReserva.getCodigoReserva()
            );

            if (!isValido) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Erro: A reserva não pertence ao voo especificado.");
            }

            reservaService.realizarEmbarque(embarqueReserva.getCodigoReserva());

            return ResponseEntity.ok("Embarque realizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno ao processar a solicitação: " + e.getMessage());
        }
    }
    
    @PostMapping("/realizarCheckin")
    public ResponseEntity<String> realizaCheckin(@RequestBody ReservaDTO reserva){
    	try {
	    	reservaService.realizarCheckin(reserva.getCodigoReserva());
	    	return ResponseEntity.ok("Check-in realizado com sucesso.");
    	 } catch (IllegalArgumentException e) {
    	        return ResponseEntity
    	            .status(HttpStatus.NOT_FOUND)
    	            .body("Erro: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Erro interno ao processar a solicitação: " + e.getMessage());
	    }
    }
    
}