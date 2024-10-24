package com.br.Auth.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.Auth.model.Login;
import com.br.Auth.repository.LoginRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginREST {

    @Autowired
    private LoginRepository loginRepository;

    // Método para buscar todos os usuários e convertê-los em DTOs
    public List<Login> getAllLogins() {
        List<Login> Logins = loginRepository.findAll();
        return Logins.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para salvar um novo usuário
    public Login saveLogin(Login login) {
        Login convertedLogin = convertToEntity(login);
        Login savedLogin = loginRepository.save(convertedLogin);
        return convertToDTO(savedLogin);
    }

    // Conversão de Login para Login
    private Login convertToDTO(Login login) {
        Login newLogin = new Login();
        newLogin.setNome(login.getNome());
//        newLogin.setEmail(login.getEmail());
        return newLogin;
    }

    // Conversão de Login para Login (Entidade)
    private Login convertToEntity(Login Login) {
        Login login = new Login();
        login.setNome(login.getNome());
//        login.setEmail(login.getEmail());
        return login;
    }
}