package controller;

import dto.AeroportoDTO;
import dto.VooDTO;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.VooService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/voos")
public class VooController {

    private final VooService vooService;

    public VooController(VooService vooService) {
        this.vooService = vooService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarVoo(@RequestBody VooDTO vooDTO) {
        try {
            VooDTO vooCadastrado = vooService.cadastrarVoo(vooDTO);
            return ResponseEntity.ok(vooCadastrado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao cadastrar o voo.");
        }
    }

    @GetMapping
    public ResponseEntity<List<VooDTO>> buscarVoos(@RequestParam String aeroportoOrigem, @RequestParam String aeroportoDestino) {

        List<VooDTO> voos = vooService.buscarVoos(aeroportoOrigem, aeroportoDestino);

        return ResponseEntity.ok(voos);
    }
    
    @GetMapping("/getVooByCodigo/{codigoVoo}")
    public ResponseEntity<VooDTO> buscarVooPorCodigo(@PathVariable String codigoVoo) {
        try {
            VooDTO voo = vooService.buscarVooPorCodigo(codigoVoo);
            if (voo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(voo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PostMapping("/aeroportos")
    public ResponseEntity<AeroportoDTO> cadastrarAeroporto(@RequestBody AeroportoDTO aeroportoDTO) {
        AeroportoDTO aeroportoCadastrado = vooService.cadastrarAeroporto(aeroportoDTO);
        return ResponseEntity.ok(aeroportoCadastrado);
    }

    @GetMapping("/aeroportos")
    public ResponseEntity<List<AeroportoDTO>> buscarAeroportos() {
        List<AeroportoDTO> aeroportos = vooService.buscarAeroportos();
        return ResponseEntity.ok(aeroportos);
    }
}
