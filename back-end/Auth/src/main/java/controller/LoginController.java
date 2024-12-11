package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import service.AuthService;
import dto.UserRequisitionDTO;
import models.EmailRequest;
import models.LoginRequisition;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import dto.UsuarioDTO;

@RestController
@RequestMapping("/Auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserRequisitionDTO> logar(@RequestBody LoginRequisition login) {
        try {
            UserRequisitionDTO user = authService.logar(login);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/InativarCadastro")
    public ResponseEntity<String> inativarCadastro(@RequestBody EmailRequest emailRequest) {
        try {
            authService.inativarCadastro(emailRequest.getEmail());
            return ResponseEntity.ok("Usuário inativado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }
}

    	
//    @GetMapping("/logins")
//    public ResponseEntity< List<UsuarioDTO>> getAllLogins() {
//        try {
//            List<UsuarioDTO> usuarios = authService.buscarTodosUsuarios();
//            return ResponseEntity.ok(usuarios);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body(null);
//        }
//    }