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

    public void realizaVoo(String codigoVoo) {
        List<ReservaDTO> reservas = reservaRepository.findByCodigoVoo(codigoVoo);

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva encontrada para o voo: " + codigoVoo);
            return;
        }

        for (ReservaDTO reserva : reservas) {
            HistoricoAlteracaoDTO historico = new HistoricoAlteracaoDTO();
            historico.setCodigoReserva(reserva.getCodigoReserva());
            historico.setDataHoraAlteracao(LocalDateTime.now());
            historico.setEstadoOrigem(reserva.getEstadoReserva());

            if ("EMB".equals(reserva.getEstadoReserva())) {
                reserva.setEstadoReserva("REAL");
                historico.setEstadoDestino("REAL");
            } else {
                reserva.setEstadoReserva("NREAL");
                historico.setEstadoDestino("NREAL");
            }

            reservaRepository.save(reserva);

            historicoRepository.save(historico);

            CQRSModel requisicaoCQRS = new CQRSModel();
            requisicaoCQRS.setReserva(reserva);
            requisicaoCQRS.setHistoricoAlteracoes(historico);

            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_atualizaReservaQ, requisicaoCQRS);

            System.out.println("Reserva " + reserva.getCodigoReserva() + " atualizada para " + reserva.getEstadoReserva() +
                    " e histórico salvo.");
        }

        System.out.println("Processamento do voo " + codigoVoo + " concluído.");
    }
    
    public boolean validarReservaVoo(String codigoVoo, String codigoReserva) {
        ReservaDTO reserva = reservaRepository.findByCodigoReserva(codigoReserva);

        return reserva != null && codigoVoo.equals(reserva.getCodigoVoo());
    }
    
    public void realizarEmbarque(String codigoReserva) {
        ReservaDTO reserva = reservaRepository.findByCodigoReserva(codigoReserva);

        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }

        HistoricoAlteracaoDTO historico = new HistoricoAlteracaoDTO();
        historico.setCodigoReserva(reserva.getCodigoReserva());
        historico.setDataHoraAlteracao(LocalDateTime.now());
        historico.setEstadoOrigem(reserva.getEstadoReserva());

        reserva.setEstadoReserva("EMB");
        reservaRepository.save(reserva);

        historico.setEstadoDestino("EMB");
        historicoRepository.save(historico);

        CQRSModel requisicaoCQRS = new CQRSModel();
        requisicaoCQRS.setReserva(reserva);
        requisicaoCQRS.setHistoricoAlteracoes(historico);

        rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_atualizaReservaQ, requisicaoCQRS);

        System.out.println("Reserva " + codigoReserva + " atualizada para EMB e histórico salvo.");
    }
}