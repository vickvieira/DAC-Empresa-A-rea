package com.br.Auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.br.Auth.model.Login;

public interface LoginRepository extends MongoRepository<Login, String> {
    // Métodos customizados podem ser adicionados aqui
}