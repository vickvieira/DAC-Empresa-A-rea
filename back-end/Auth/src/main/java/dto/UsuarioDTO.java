package dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;

    private String email;

    private String senha;

    private String tipo;

    private String salt;
    
    private Boolean ativo;

    public UsuarioDTO() {}

    public UsuarioDTO(String email, String senha, String tipo, String salt) {
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.salt = salt;
    }
    
    public UsuarioDTO(String email, String senha, String tipo, String salt, Boolean ativo) {
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.salt = salt;
        this.ativo = ativo;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    
    
    public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
    public String toString() {
        return "UsuarioDTO{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}