package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dto.UsuarioDTO;
import service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/Auth")
public class LoginController {

    @Autowired
    private AuthService loginService;

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