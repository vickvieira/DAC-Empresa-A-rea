package SagaController;

import DTO.UserRequisitionDTO;
import SagaService.SagaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saga")
public class SagaController {

    private final SagaService sagaService;

    public SagaController(SagaService sagaService) {
        this.sagaService = sagaService;
    }

    @PostMapping()
    public ResponseEntity<String> processar(@RequestBody UserRequisitionDTO userData) {
        String response = sagaService.iniciarSaga(userData);
        return ResponseEntity.ok(response);
    }
}