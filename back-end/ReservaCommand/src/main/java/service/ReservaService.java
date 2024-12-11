package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import constantes.RabbitmqConstantes;
import repository.HistoricoAlteracaoRepository;
import repository.ReservaRepository;
import dto.HistoricoAlteracaoDTO;
import dto.ReservaDTO;
import models.CQRSModel;
import models.ClienteMilhas;

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

    public List<ClienteMilhas> cancelaVooERetornaClientesEMilhas(String codigoVoo) {
        List<ReservaDTO> reservas = reservaRepository.findByCodigoVoo(codigoVoo);
        List<ClienteMilhas> clientesMilhas = new ArrayList<>();

        for (ReservaDTO reserva : reservas) {
            HistoricoAlteracaoDTO hist = new HistoricoAlteracaoDTO();
            hist.setCodigoReserva(reserva.getCodigoReserva());
            hist.setDataHoraAlteracao(LocalDateTime.now());
            hist.setEstadoOrigem(reserva.getEstadoReserva());

            reserva.setEstadoReserva("CVOO");
            reservaRepository.save(reserva);

            hist.setEstadoDestino("CVOO");
            historicoRepository.save(hist);

            clientesMilhas.add(new ClienteMilhas(reserva.getIdCliente(), reserva.getMilhasGastas()));

            CQRSModel requisitionCQRS = new CQRSModel();
            requisitionCQRS.setReserva(reserva);
            requisitionCQRS.setHistoricoAlteracoes(hist);
            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_atualizaReservaQ, requisitionCQRS);
        }

        return clientesMilhas;
    }
   
}