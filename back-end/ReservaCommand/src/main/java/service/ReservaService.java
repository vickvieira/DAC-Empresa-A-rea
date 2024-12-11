package service;

import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.HistoricoAlteracaoRepository;
import repository.ReservaRepository;
import dto.HistoricoAlteracaoDTO;
import dto.ReservaDTO;

@Service
public class ReservaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private HistoricoAlteracaoRepository historicoRepository;

    public ReservaDTO cadastrarReserva(ReservaDTO reserva) {
        String codigoReserva = gerarCodigoReserva();
        reserva.setCodigoReserva(codigoReserva);

        ReservaDTO reservaSalva = reservaRepository.save(reserva);
        return reservaSalva;
    }

    private String gerarCodigoReserva() {
        Random random = new Random();

        String letras = random.ints(3, 'A', 'Z' + 1)
                               .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                               .toString();

        String numeros = String.format("%03d", random.nextInt(1000));

        return letras + numeros;
    }
    
    public void enviaMensagem(String nomeFila, Object mensagem) {
        rabbitTemplate.convertAndSend(nomeFila, mensagem);
    }
    
    public ReservaDTO buscarPorCodigoReserva(String codigoReserva) {
        return reservaRepository.findByCodigoReserva(codigoReserva);
    }
    
    public ReservaDTO atualizarReserva(ReservaDTO reserva) {
        return reservaRepository.save(reserva);
    }
    
    public void insereHistorico(HistoricoAlteracaoDTO hist) {
    	historicoRepository.save(hist);
    }
}