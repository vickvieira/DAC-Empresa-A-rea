package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.ClientesDTO;
import service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Criar um novo cliente
    @PostMapping
    public ResponseEntity<ClientesDTO> criarCliente(@RequestBody ClientesDTO clienteDTO) {
        ClientesDTO clienteCriado = clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.ok(clienteCriado);
    }
    
    @GetMapping
    public ResponseEntity<List<ClientesDTO>> listarClientes() {
        List<ClientesDTO> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }
}