package dto;

import java.io.Serializable;

public class UserRequisitionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String tipo;
    private Boolean ativo;

    // Construtor vazio
    public UserRequisitionDTO() {
    }
    
    public UserRequisitionDTO(String email, String tipo, Boolean ativo) {
        this.email = email;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    // Construtor com parâmetros
    public UserRequisitionDTO(String email, String tipo) {
        this.email = email;
        this.tipo = tipo;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
    
    

}