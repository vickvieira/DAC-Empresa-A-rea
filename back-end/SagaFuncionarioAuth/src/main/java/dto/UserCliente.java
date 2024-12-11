package dto;

import java.io.Serializable;

public class UserCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private FuncionarioDTO clienteDTO;
    private UserRequisitionDTO userRequisitionDTO;
    private String status;
    private String mensagem;

    public UserCliente() {}

    public UserCliente(FuncionarioDTO clienteDTO, UserRequisitionDTO userRequisitionDTO) {
        this.clienteDTO = clienteDTO;
        this.userRequisitionDTO = userRequisitionDTO;
    }

    // Getters e Setters
    public FuncionarioDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(FuncionarioDTO clienteDTO) {
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