package dto;

import java.io.Serializable;

public class UserCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private ClientesDTO clienteDTO;
    private UserRequisitionDTO userRequisitionDTO;
    private String status;
    private String mensagem;

    // Construtor padrão
    public UserCliente() {}

    // Construtor com parâmetros
    public UserCliente(ClientesDTO clienteDTO, UserRequisitionDTO userRequisitionDTO) {
        this.clienteDTO = clienteDTO;
        this.userRequisitionDTO = userRequisitionDTO;
    }

    // Getters e Setters
    public ClientesDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClientesDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

	public UserRequisitionDTO getUserRequisitionDTO() {
		return userRequisitionDTO;
	}

	public void setUserRequisitionDTO(UserRequisitionDTO userRequisitionDTO) {
		this.userRequisitionDTO = userRequisitionDTO;
	}

	@Override
	public String toString() {
		return "UserCliente [clienteDTO=" + clienteDTO + ", userRequisitionDTO=" + userRequisitionDTO + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}