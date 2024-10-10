package com.br.Auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.br.Auth.model.Login;
import com.br.Auth.rest.LoginREST;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class LoginController {

    @Autowired
    private LoginREST usuarioService;

    @GetMapping
    public List<Login> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @PostMapping
    public Login createUsuario(@RequestBody Login usuarioDTO) {
        return usuarioService.saveUsuario(usuarioDTO);
    }
}