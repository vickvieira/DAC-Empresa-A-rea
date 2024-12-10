export type EstadoVoo = 'CONFIRMADO' | 'CANCELADO' | 'REALIZADO';
export interface Voo {
  // length: number;
  codigo: string;
  dataHora: string;
  origem: string;
  destino: string;
  valorPassagem: number;
  quantidadePoltronas: number;
  poltronasOcupadas: number;
  status: EstadoVoo;
  id?: string;

  // mais coisa ?
}
