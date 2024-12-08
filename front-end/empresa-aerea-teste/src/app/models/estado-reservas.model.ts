export type EstadoReserva =
  | 'RESERVADO'
  | 'AGUARDANDO_CHECKIN'
  | 'CHECKED_IN'
  | 'EMBARCADO'
  | 'REALIZADO'
  | 'NÃO_REALIZADO'
  | 'CANCELADO_VOO'
  | 'CANCELADO_RESERVA';

export interface HistoricoEstadoReserva {
  reservaCodigo: string; // Referência ao código da reserva
  dataHoraAlteracao: string;
  estadoOrigem: EstadoReserva; // Estado antes da alteração
  estadoDestino: EstadoReserva; // Estado após a alteração
  alteradoPor: string; // Identificador ou nome do responsável pela alteração? n sei se precisa
  id: string; // Identificador único do histórico
}
