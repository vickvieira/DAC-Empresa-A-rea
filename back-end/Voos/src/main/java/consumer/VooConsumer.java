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
	    
	    @RabbitListener(queues = RabbitmqConstantes.VOO_CANCELA)
	    public void consumidorCancela(SagaReservaRequisition requisition) {
	        try {
	            System.out.println("Processando cancelamento de reserva para o voo: " + requisition.getReserva().getCodigoVoo());

	            VooDTO voo = vooService.buscarVooPorCodigo(requisition.getReserva().getCodigoVoo());
	            if (voo == null) {
	                throw new IllegalArgumentException("Voo não encontrado: " + requisition.getReserva().getCodigoVoo());
	            }

	            int assentosOcupados = voo.getQuantidadePoltronasOcupadas() - requisition.getReserva().getQuantidadePoltronasReservadas();
	            if (assentosOcupados < 0) {
	                throw new IllegalStateException("Quantidade de assentos ocupados não pode ser negativa");
	            }

	            voo.setQuantidadePoltronasOcupadas(assentosOcupados);
	            vooService.atualizarVoo(voo);

	            rabbitTemplate.convertAndSend(RabbitmqConstantes.VOO_CANCELA_ATUALIZADA, requisition);

	            System.out.println("Cancelamento processado com sucesso para o voo: " + requisition.getReserva().getCodigoVoo());

	        } catch (Exception e) {
	            System.err.println("Erro ao processar cancelamento: " + e.getMessage());
	        }
	    }
	    
	    @RabbitListener(queues = RabbitmqConstantes.VOO_CANCELA_VOO)
	    public void consumidorCancelaVoo(VooDTO vooRequisicao) {
	        try {
	            if (vooRequisicao == null || vooRequisicao.getCodigoVoo() == null) {
	                System.out.println("Código do voo é nulo ou inválido.");
	                return;
	            }

	            VooDTO voo = vooService.buscarVooPorCodigo(vooRequisicao.getCodigoVoo());

	            if (voo == null) {
	                System.out.println("Voo não encontrado: " + vooRequisicao.getCodigoVoo());
	                return;
	            }

	            System.out.println("Status atual do voo: " + voo.getStatus());
	            voo.setStatus("CANCELADO");
	            vooService.atualizarVoo(voo);

	            rabbitTemplate.convertAndSend(RabbitmqConstantes.VOO_CANCELA_voo_ATUALIZADA, voo);
	            System.out.println("Voo cancelado com sucesso e mensagem enviada para a fila: " + RabbitmqConstantes.VOO_CANCELA_voo_ATUALIZADA);

	        } catch (Exception e) {
	            System.err.println("Erro ao processar cancelamento do voo: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    @RabbitListener(queues = RabbitmqConstantes.FILA_REALIZA_VOO)
	    public void consumidorRealizaVoo(VooDTO vooRequisicao) {
    	try {
            if (vooRequisicao == null || vooRequisicao.getCodigoVoo() == null) {
                System.out.println("Código do voo é nulo ou inválido.");
                return;
            }
            VooDTO voo = vooService.buscarVooPorCodigo(vooRequisicao.getCodigoVoo());
            if (voo == null) {
                System.out.println("Voo não encontrado: " + vooRequisicao.getCodigoVoo());
                return;
            }
            
            System.out.println("Status atual do voo: " + voo.getStatus());
            voo.setStatus("REALIZADO");
            vooService.atualizarVoo(voo);

            rabbitTemplate.convertAndSend(RabbitmqConstantes.FILA_REALIZA_VOO_ATUALIZA, voo);
            System.out.println("Voo cancelado com sucesso e mensagem enviada para a fila: " + RabbitmqConstantes.FILA_REALIZA_VOO_ATUALIZA);

	        } catch (Exception e) {
	            System.err.println("Erro ao processar cancelamento do voo: " + e.getMessage());
	            e.printStackTrace();
	        }
            
	    }
}
