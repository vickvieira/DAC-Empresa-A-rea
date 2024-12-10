package consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constantes.RabbitmqConstantes;
import dto.VooDTO;
import models.SagaReservaRequisition;
import service.VooService;

@Component
public class VooConsumer  {

	 @Autowired
	    private VooService vooService;

	    @Autowired
	    private RabbitTemplate rabbitTemplate;

	    @RabbitListener(queues = RabbitmqConstantes.FILA_VOO)
	    public void consumidor(SagaReservaRequisition requisition) {
	    	System.out.print("aaaaaaaaaa fila voo");
	        try {
	        	System.out.print("Processando fila voo");
	            VooDTO voo = vooService.buscarVooPorCodigo(requisition.getReserva().getCodigoVoo());
	            if (voo == null) {
	                throw new IllegalArgumentException("Voo não encontrado: " + requisition.getReserva().getCodigoVoo());
	            }

	            int assentosDisponiveis = voo.getQuantidadePoltronasTotal() - voo.getQuantidadePoltronasOcupadas();
	            if (assentosDisponiveis < requisition.getReserva().getQuantidadePoltronasReservadas()) {
	                throw new IllegalStateException("Não há assentos suficientes no voo " + voo.getCodigoVoo());
	            }

	            voo.setQuantidadePoltronasOcupadas(
	                voo.getQuantidadePoltronasOcupadas() + requisition.getReserva().getQuantidadePoltronasReservadas()
	            );
	            vooService.atualizarVoo(voo);

	            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_VOO_ATUALIZADO, requisition);

	        } catch (Exception e) {
	            System.err.println("Erro ao processar reserva: " + e.getMessage());
	        }
	    }
}
