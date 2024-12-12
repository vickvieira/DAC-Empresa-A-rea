package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.ClientesDTO;
import dto.MilhasDTO;
import models.ComprarMilhasRequest;
import service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClientesDTO>> getClientes() {
        List<ClientesDTO> clientes = clienteService.buscarTodosClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientesDTO> buscarClientePorId(@PathVariable Long id) {
        try {
            ClientesDTO cliente = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/comprar-milhas")
    public ResponseEntity<String> comprarMilhas(@RequestBody ComprarMilhasRequest request) {
        clienteService.comprarMilhas(request.getClienteId(), request.getValor());
        return ResponseEntity.ok("Milhas compradas com sucesso!");
    }

    @GetMapping("/extrato-milhas/{id}")
    public ResponseEntity<List<MilhasDTO>> consultarExtratoMilhas(@PathVariable("id") Long id) {
        List<MilhasDTO> extrato = clienteService.consultarExtratoMilhas(id);
        return ResponseEntity.ok(extrato);
    }
}