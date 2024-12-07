import { EstadoReserva } from './estado-reservas.model';
import { Voo } from './voo.model';

export interface Reserva {
  codigo: string;
  dataHoraReserva: string;
  clienteId: number;
  voo: Voo;
  valorGasto: number;
  milhasUtilizadas: number;
  status: EstadoReserva;
  expandido?: boolean;
  id: string;
}
