package service;

import dto.ClientesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ClienteRepository;

import java.util.List;

@Service
public class ClienteService {

	@Autowired
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Criar um novo cliente
    public ClientesDTO salvarCliente(ClientesDTO cliente) {
        return clienteRepository.save(cliente); // Salva o cliente no banco de dados
    }

    // Listar todos os clientes
    public List<ClientesDTO> listarTodos() {
        return clienteRepository.findAll(); // Retorna todos os clientes do banco
    }

    // Buscar cliente por ID
    public ClientesDTO buscarPorId(Long id) {
        return clienteRepository.findById(id) 
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!")); // Busca cliente por ID
    }

    // Atualizar cliente
    public ClientesDTO atualizarCliente(Long id, ClientesDTO clienteAtualizado) {
        ClientesDTO clienteExistente = buscarPorId(id); // Verifica se o cliente existe
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setTipo(clienteAtualizado.getTipo());
        clienteExistente.setRuaNumero(clienteAtualizado.getRuaNumero());
        clienteExistente.setComplemento(clienteAtualizado.getComplemento());
        clienteExistente.setCep(clienteAtualizado.getCep());
        clienteExistente.setCidade(clienteAtualizado.getCidade());
        clienteExistente.setEstado(clienteAtualizado.getEstado());
        clienteExistente.setMilhas(clienteAtualizado.getMilhas());
        return clienteRepository.save(clienteExistente); // Atualiza o cliente no banco
    }

    // Deletar cliente
    public void deletarCliente(Long id) {
        ClientesDTO cliente = buscarPorId(id); // Verifica se o cliente existe
        clienteRepository.delete(cliente); // Remove o cliente do banco
    }
}