package DTO;

import java.io.Serializable;

public class UserCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private ClientesDTO clienteDTO;
    private UserRequisitionDTO userRequisitionDTO;

    public UserCliente() {
    }

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

}