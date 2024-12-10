package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import DTO.ClientesDTO;
import DTO.MilhasDTO;
import service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClientesDTO> criarCliente(@RequestBody ClientesDTO clienteDTO) {
        ClientesDTO clienteCriado = clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.ok(clienteCriado);
    }

    @PostMapping("/{id}/comprar-milhas")
    public ResponseEntity<String> comprarMilhas(@PathVariable Long id, @RequestParam double valor) {
        clienteService.comprarMilhas(id, valor);
        return ResponseEntity.ok("Milhas compradas com sucesso!");
    }

    @GetMapping("/{id}/extrato-milhas")
    public ResponseEntity<List<MilhasDTO>> consultarExtratoMilhas(@PathVariable Long id) {
        List<MilhasDTO> extrato = clienteService.consultarExtratoMilhas(id);
        return ResponseEntity.ok(extrato);
    }
}