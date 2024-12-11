package DTO;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;
    private String tipo;
    private String salt;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String email, String senha, String tipo, String salt) {
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.salt = salt;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}