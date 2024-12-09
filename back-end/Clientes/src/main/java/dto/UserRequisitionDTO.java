package dto;

import java.io.Serializable;

public class UserRequisitionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String tipo;

    // Construtor vazio
    public UserRequisitionDTO() {
    }

    // Construtor com parâmetros
    public UserRequisitionDTO(String email) {
        this.email = email;
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
    
    

}