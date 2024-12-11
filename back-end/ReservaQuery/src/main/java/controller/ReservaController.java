package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dto.HistoricoAlteracaoDTO;
import dto.ReservaDTO;
import service.ReservaService;

import java.util.List;

@RestController
@RequestMapping("/reservaquery")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/historico/{codigoReserva}")
    public ResponseEntity<List<HistoricoAlteracaoDTO>> getListHistoricoReserva(@PathVariable String codigoReserva) {
        try {
            List<HistoricoAlteracaoDTO> historicoReservas = reservaService.buscarHistoricoReservas(codigoReserva);
            return ResponseEntity.ok(historicoReservas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("getReserva/{codigoReserva}")
    public ResponseEntity<ReservaDTO> getReservaPorCodigo(@PathVariable String codigoReserva) {
        try {
            ReservaDTO reserva = reservaService.buscarPorCodigoReserva(codigoReserva);
            return ResponseEntity.ok(reserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/getByCliente/{clienteId}")
    public ResponseEntity<List<ReservaDTO>> getReservasPorCliente(@PathVariable Long clienteId) {
        try {
            List<ReservaDTO> reservas = reservaService.buscarReservasPorCliente(clienteId);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
        
}