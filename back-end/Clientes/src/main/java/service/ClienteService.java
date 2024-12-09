package service;

import dto.ClientesDTO;
import dto.MilhasDTO;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ClienteRepository;
import repository.MilhasRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService {

	@Autowired
    private final ClienteRepository clienteRepository;
	
	@Autowired
    private final MilhasRepository milhasRepository;
	
	@Autowired
	RabbitTemplate rabbitTemplate;

    public ClienteService(ClienteRepository clienteRepository, MilhasRepository milhasRepository ) {
        this.clienteRepository = clienteRepository;
        this.milhasRepository = milhasRepository;
    }

    
    public void enviaMensagem(String nomeFila, Object mensagem) {
        rabbitTemplate.convertAndSend(nomeFila, mensagem);

    }
    
    public ClientesDTO cadastrarCliente(ClientesDTO cliente) {
    	
        ClientesDTO clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
        ClientesDTO clienteExistenteEMAIL = clienteRepository.findByEmail(cliente.getEmail());

        if (clienteExistente  != null && clienteExistenteEMAIL != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com o CPF: " + cliente.getCpf());
        }

        return clienteRepository.save(cliente);
    }

    public void comprarMilhas(Long clienteId, double valor) {
        final double PROPORCAO = 5.0; // 1 milha por R$ 5,00

        ClientesDTO cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        double milhasCompradas = valor / PROPORCAO;

        cliente.setMilhas(cliente.getMilhas() + milhasCompradas);
        clienteRepository.save(cliente);

        MilhasDTO transacao = new MilhasDTO(cliente, LocalDateTime.now(), milhasCompradas, "entrada", "COMPRA DE MILHAS");
        milhasRepository.save(transacao);

        enviaMensagem("fila-milhas", transacao);
    }

    public List<MilhasDTO> consultarExtratoMilhas(Long clienteId) {

        ClientesDTO cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        return milhasRepository.findByCliente(cliente);
    }

}