package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import service.AuthService;
import dto.UserRequisitionDTO;
import models.LoginRequisition;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(null);
        }
    }
    	
    	
//    @PostMapping("/cadastrar")
//    public ResponseEntity<String> cadastrar(@RequestBody UsuarioDTO loginDTO) {
//        try {
//            loginService.cadastrarUsuario(loginDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Erro ao cadastrar usuário: " + e.getMessage());
//        }
//    }
}