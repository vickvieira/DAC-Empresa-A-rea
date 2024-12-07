import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Reserva } from '../models/reserva.model';
import { catchError, map, Observable, switchMap, throwError } from 'rxjs';
import { MilhasService } from './milhas.service';
import { Voo } from '../models/voo.model';

@Injectable({
  providedIn: 'root',
})
export class ReservaService {
  private apiUrl = 'http://localhost:3000/reservas';
  reserva!: Reserva;
  constructor(private http: HttpClient, private milhasService: MilhasService) {}

  // Método para gerar o código de reserva único
  private gerarCodigoReserva(): string {
    const letras = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const numeros = '0123456789';
    const codigoLetras = Array.from(
      { length: 3 },
      () => letras[Math.floor(Math.random() * letras.length)]
    ).join('');
    const codigoNumeros = Array.from(
      { length: 3 },
      () => numeros[Math.floor(Math.random() * numeros.length)]
    ).join('');
    return `${codigoLetras}${codigoNumeros}`;
  }

  getReservasByClienteId(clienteId: number): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.apiUrl}?clienteId=${clienteId}`);
  }

  //cancelar reserva na verdade era só atualizar o status dela pra cancelada.
  //ela não é para ser de fato deletada

  /* esse é o cancelarReserva velho, que deletava ela
  cancelarReserva(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
    
    //status: Reserva cancelada
  }
  */
  cancelarReserva(reservaCancelada: Reserva): Observable<Reserva> {
    reservaCancelada.status = 'CANCELADO_RESERVA'; //pq o cliente cancelou a reserva, não o func o voo

    return this.http.put<Reserva>(
      `${this.apiUrl}/${reservaCancelada.id}`,
      reservaCancelada
    );

    //status: Reserva cancelada
  }

  criarReserva(
    clienteId: number,
    voo: Voo,
    quantidadePassagens: number,
    valorTotal: number,
    milhasUtilizadas: number
  ): Observable<Reserva> {
    const codigoReserva = this.gerarCodigoReserva();
    const dataHoraReserva = new Date().toISOString();

    const novaReserva: Reserva = {
      codigo: codigoReserva,
      dataHoraReserva: dataHoraReserva,
      clienteId: clienteId,
      voo: voo,
      valorGasto: valorTotal,
      milhasUtilizadas: milhasUtilizadas,
      status: 'AGUARDANDO_CHECKIN',
      id: '',
    };

    // Atualiza o saldo de milhas do cliente
    this.milhasService
      .atualizarMilhas(clienteId, -milhasUtilizadas)
      .subscribe();

    return this.http.post<Reserva>(this.apiUrl, novaReserva);
  }

  confirmarEmbarque(
    codigoReserva: string,
    codigoVoo: string
  ): Observable<Reserva> {
    return this.http
      .get<Reserva[]>(`${this.apiUrl}?codigo=${codigoReserva}`)
      .pipe(
        map((reservas) => {
          if (!reservas.length) {
            throw new Error('Reserva não encontrada.');
          }

          const reserva = reservas[0]; // Assume que o código de reserva é único
          if (reserva.voo.codigo !== codigoVoo) {
            throw new Error('A reserva não pertence ao voo informado.');
          }
          if (reserva.status !== 'AGUARDANDO_CHECKIN') {
            throw new Error(
              'A reserva não está no estado "Aguardando Check-In".'
            );
          }

          // Atualiza o status para EMBARCADO
          reserva.status = 'EMBARCADO';
          return reserva;
        }),
        switchMap((reserva) =>
          this.http.put<Reserva>(`${this.apiUrl}/${reserva.id}`, reserva)
        ),
        catchError((error) => {
          console.error('Erro ao confirmar embarque:', error);
          return throwError(
            () => new Error(error.message || 'Erro desconhecido.')
          );
        })
      );
  }

  getReservasPorCodigoVoo(codigoVoo: string): Observable<any[]> {
    return this.http
      .get<any[]>(this.apiUrl)
      .pipe(
        map((reservas) =>
          reservas.filter((reserva) => reserva.voo.codigo === codigoVoo)
        )
      );
  }
}

// mais metodos
