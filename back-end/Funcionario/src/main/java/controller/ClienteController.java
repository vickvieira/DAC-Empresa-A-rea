package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.FuncionarioDTO;
import service.FuncService;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class ClienteController {

    private final FuncService  clienteService;

    public ClienteController(FuncService clienteService) {
        this.clienteService = clienteService;
    }

    
    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> getFuncs() {
        List<FuncionarioDTO> clientes = clienteService.buscarTodosClientes();
        return ResponseEntity.ok(clientes);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioDTO funcionarioAtualizado) {
        try {
            FuncionarioDTO funcionario = clienteService.atualizarFuncionario(id, funcionarioAtualizado);
            return ResponseEntity.ok(funcionario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}