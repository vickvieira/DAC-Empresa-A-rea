package service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReservaRepository;
import dto.ReservaDTO;

@Service
public class ReservaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ReservaRepository reservaRepository;

    public ReservaDTO cadastrarReserva(ReservaDTO reserva) {
        
        ReservaDTO reservaSalva = reservaRepository.save(reserva);
        return reservaSalva;
    }
    
    public void enviaMensagem(String nomeFila, Object mensagem) {
        rabbitTemplate.convertAndSend(nomeFila, mensagem);
    }
}