package DTO;

import java.io.Serializable;

public class UserRequisitionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ClientesDTO clienteDTO;
    private UsuarioDTO usuarioDTO;

    // Construtor padrão
    public UserRequisitionDTO() {}

    // Construtor com parâmetros
    public UserRequisitionDTO(ClientesDTO clienteDTO, UsuarioDTO usuarioDTO) {
        this.clienteDTO = clienteDTO;
        this.usuarioDTO = usuarioDTO;
    }

    // Getters e Setters
    public ClientesDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClientesDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }
}