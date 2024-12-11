package service;

import dto.FuncionarioDTO;
//import models.ClienteReserva;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FuncRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FuncService {

    @Autowired
    private final FuncRepository clienteRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public FuncService(FuncRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void enviaMensagem(String nomeFila, Object mensagem) {
        rabbitTemplate.convertAndSend(nomeFila, mensagem);

    }

    public FuncionarioDTO cadastrarCliente(FuncionarioDTO cliente) {

    	FuncionarioDTO clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
    	FuncionarioDTO clienteExistenteEMAIL = clienteRepository.findByEmail(cliente.getEmail());

        if (clienteExistente != null && clienteExistenteEMAIL != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com o CPF: " + cliente.getCpf());
        }

        return clienteRepository.save(cliente);
    }



    public List<FuncionarioDTO> buscarTodosClientes() {
        return clienteRepository.findAll();
    }

    
    public FuncionarioDTO atualizarFuncionario(Long id, FuncionarioDTO funcionarioAtualizado) {
        FuncionarioDTO funcionarioExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado com ID: " + id));

        funcionarioExistente.setNome(funcionarioAtualizado.getNome());
        funcionarioExistente.setEmail(funcionarioAtualizado.getEmail());
        funcionarioExistente.setTelefone(funcionarioAtualizado.getTelefone());

        return clienteRepository.save(funcionarioExistente);
    }
}