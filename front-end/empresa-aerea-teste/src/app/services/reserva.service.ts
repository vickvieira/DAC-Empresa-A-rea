import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Reserva } from '../models/reserva.model';
import {
  catchError,
  forkJoin,
  map,
  Observable,
  of,
  switchMap,
  tap,
  throwError,
} from 'rxjs';
import { MilhasService } from './milhas.service';
import { Voo } from '../models/voo.model';
import { VooService } from './voo.service';

@Injectable({
  providedIn: 'root',
})
export class ReservaService {
  private apiUrl = 'http://localhost:3000/reservas';
  reserva!: Reserva;
  constructor(
    private http: HttpClient,
    private milhasService: MilhasService,
    private vooService: VooService
  ) { }

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
  cancelarReserva(reserva: Reserva): Observable<Reserva> {
    console.log('[DEBUG] Cancelando reserva:', reserva);

    // Remove campos não relacionados antes de enviar
    const reservaAtualizada: Partial<Reserva> = {
      id: reserva.id,
      codigo: reserva.codigo,
      clienteId: reserva.clienteId,
      vooCodigo: reserva.vooCodigo,
      dataHoraReserva: reserva.dataHoraReserva,
      status: 'CANCELADO_RESERVA',
      valorGasto: reserva.valorGasto,
      milhasUtilizadas: reserva.milhasUtilizadas,
    };

    console.log('[DEBUG] Enviando atualização de reserva:', reservaAtualizada);

    return this.http
      .put<Reserva>(`${this.apiUrl}/${reserva.id}`, reservaAtualizada)
      .pipe(
        tap((reservaAtualizada) =>
          console.log(
            '[DEBUG] Reserva atualizada no backend:',
            reservaAtualizada
          )
        ),
        catchError((err) => {
          console.error('[ERROR] Falha ao cancelar reserva:', err);
          throw err;
        })
      );
  }

  criarReserva(
    clienteId: number,
    codigoVoo: string,
    quantidadePassagens: number,
    valorTotal: number,
    milhasUtilizadas: number
  ): Observable<Reserva> {
    const codigoReserva = this.gerarCodigoReserva();
    const dataHoraReserva = new Date().toISOString();

    const novaReserva: Reserva = {
      // id: '', // Será gerado pelo backend
      codigo: this.gerarCodigoReserva(),
      clienteId: clienteId, // Converte para string se necessário
      vooCodigo: codigoVoo,
      dataHoraReserva: new Date().toISOString(),
      status: 'AGUARDANDO_CHECKIN', // Status inicial
      valorGasto: valorTotal,
      milhasUtilizadas: milhasUtilizadas,
    };

    console.log('[DEBUG] Enviando reserva para criação:', novaReserva);

    // return this.http.post<Reserva>(this.apiUrl, novaReserva).pipe(
    //   tap((reservaCriada) =>
    //     console.log('[DEBUG] Reserva criada com sucesso:', reservaCriada)
    //   ),

    //   catchError((err) => {
    //     console.error('[ERROR] Falha ao criar reserva:', err);
    //     throw err; // Repropaga o erro para tratamento no componente
    //   })
    // );
    return this.http.post<Reserva>(this.apiUrl, novaReserva).pipe(
      switchMap((reservaCriada) => {
        console.log('[DEBUG] Reserva criada com sucesso:', reservaCriada);

        // Atualiza o número de assentos ocupados no voo
        return this.vooService
          .atualizarAssentosOcupados(codigoVoo, quantidadePassagens)
          .pipe(
            map(() => {
              console.log(
                `[DEBUG] Assentos atualizados para o voo ${codigoVoo}.`
              );
              return reservaCriada; // Retorna a reserva criada após a atualização
            })
          );
      }),
      catchError((error) => {
        console.error(
          '[ERROR] Falha ao criar reserva ou atualizar assentos:',
          error
        );
        return throwError(() => new Error('Erro ao criar reserva.'));
      })
    );
  }

  confirmarEmbarque(
    codigoReserva: string,
    codigoVoo: string
  ): Observable<Reserva> {
    return this.http
      .get<Reserva[]>(`${this.apiUrl}?codigo=${codigoReserva}`)
      .pipe(
        switchMap((reservas) => {
          const reserva = reservas[0];
          if (!reserva) {
            throw new Error('Reserva não encontrada.');
          }

          if (reserva.vooCodigo !== codigoVoo) {
            throw new Error('A reserva não pertence ao voo informado.');
          }

          if (reserva.status !== 'CHECKED_IN') {
            throw new Error(
              'A reserva não está no estado "Check-In Realizado".'
            );
          }

          // Atualiza o status para EMBARCADO
          reserva.status = 'EMBARCADO';
          return this.http.put<Reserva>(
            `${this.apiUrl}/${reserva.id}`,
            reserva
          );
        }),
        catchError((error) => {
          console.error('Erro ao confirmar embarque:', error);
          return throwError(
            () => new Error(error.message || 'Erro desconhecido.')
          );
        })
      );
  }

  getReservasPorCodigoVoo(codigoVoo: string): Observable<Reserva[]> {
    return this.http
      .get<Reserva[]>(this.apiUrl)
      .pipe(
        map((reservas) =>
          reservas.filter((reserva) => reserva.vooCodigo === codigoVoo)
        )
      );
  }

  getReservasProntasParaCheckin(): Observable<Reserva[]> {
    return this.http
      .get<Reserva[]>(this.apiUrl)
      .pipe(
        map((reservas) =>
          reservas.filter((reserva) => reserva.status === 'AGUARDANDO_CHECKIN')
        )
      );
  }



  atualizarReservasParaCancelamento(codigoVoo: string): Observable<Reserva[]> {
    return this.getReservasPorCodigoVoo(codigoVoo).pipe(
      switchMap((reservas) => {
        const reservasAtualizadas: Observable<Reserva>[] = reservas.map(
          (reserva) => {
            // Atualiza o status da reserva para CANCELADO_VOO
            const reservaAtualizada = { ...reserva, status: 'CANCELADO_VOO' };

            // Reembolsa as milhas, se aplicável
            if (reserva.milhasUtilizadas > 0) {
              console.log(
                `[DEBUG] Reembolsando ${reserva.milhasUtilizadas} milhas ao cliente ${reserva.clienteId}.`
              );
              this.milhasService
                .atualizarMilhas(
                  Number(reserva.clienteId),
                  reserva.milhasUtilizadas
                )
                .subscribe();
            }

            // Atualiza a reserva no backend
            return this.http.put<Reserva>(
              `${this.apiUrl}/${reserva.id}`,
              reservaAtualizada
            );
          }
        );

        // Aguarda todas as atualizações das reservas
        return forkJoin(reservasAtualizadas);
      }),
      tap((reservasAtualizadas) => {
        console.log(
          '[DEBUG] Reservas atualizadas para cancelamento de voo:',
          reservasAtualizadas
        );
      }),
      catchError((err) => {
        console.error(
          '[ERROR] Falha ao atualizar reservas para cancelamento de voo:',
          err
        );
        throw err;
      })
    );
  }

  reembolsarMilhasReserva(reserva: Reserva): Observable<any> {
    if (reserva.milhasUtilizadas > 0) {
      console.log(
        `[DEBUG] Reembolsando ${reserva.milhasUtilizadas} milhas para o cliente ${reserva.clienteId}.`
      );
      return this.milhasService.reembolsarMilhas(
        reserva.clienteId,
        `Cancelamento do voo ${reserva.vooCodigo}`,
        reserva.milhasUtilizadas
      );
    } else {
      console.log(
        `[DEBUG] Nenhuma milha utilizada na reserva ${reserva.codigo}, nada a reembolsar.`
      );
      return of(null); // Retorna um Observable vazio para evitar erros
    }
  }

  atualizarReservasParaRealizacao(codigoVoo: string): Observable<Reserva[]> {
    console.log(
      `[DEBUG] Atualizando reservas para o voo realizado: ${codigoVoo}`
    );

    return this.getReservasPorCodigoVoo(codigoVoo).pipe(
      switchMap((reservas) => {
        if (!reservas.length) {
          console.warn(
            `[DEBUG] Nenhuma reserva encontrada para o voo ${codigoVoo}.`
          );
          return of([]); // Retorna lista vazia
        }

        console.log(
          `[DEBUG] Reservas encontradas para o voo ${codigoVoo}:`,
          reservas
        );

        const atualizacoes = reservas.map((reserva) => {
          reserva.status =
            reserva.status === 'EMBARCADO' ? 'REALIZADO' : 'NÃO_REALIZADO';
          return this.http.put<Reserva>(
            `${this.apiUrl}/${reserva.id}`,
            reserva
          );
        });

        return forkJoin(atualizacoes);
      }),
      catchError((error) => {
        console.error(
          `[ERROR] Erro ao atualizar reservas para o voo ${codigoVoo}:`,
          error
        );
        return throwError(
          () => new Error(error.message || 'Erro ao atualizar reservas.')
        );
      })
    );
  }

  
  getReservaPorCodigoReserva(codigoReserva: string): Observable<Reserva[]> {
    return this.http
      .get<Reserva[]>(this.apiUrl)
      .pipe(
        map((reservas) =>
          reservas.filter((reserva) => reserva.codigo === codigoReserva)
        )
      );
  }
  

  confirmarCheckin(reserva: Reserva): Observable<Reserva> {
    console.log('[DEBUG] Confirmar Checkin:', reserva);

    // Remove campos não relacionados antes de enviar
    const reservaAtualizada: Partial<Reserva> = {
      id: reserva.id,
      codigo: reserva.codigo,
      clienteId: reserva.clienteId,
      vooCodigo: reserva.vooCodigo,
      dataHoraReserva: reserva.dataHoraReserva,
      status: 'CHECKED_IN',
      valorGasto: reserva.valorGasto,
      milhasUtilizadas: reserva.milhasUtilizadas,
    };

    console.log('[DEBUG] Enviando atualização de reserva:', reservaAtualizada);

    return this.http
      .put<Reserva>(`${this.apiUrl}/${reserva.id}`, reservaAtualizada)
      .pipe(
        tap((reservaAtualizada) =>
          console.log(
            '[DEBUG] Reserva atualizada no backend:',
            reservaAtualizada
          )
        ),
        catchError((err) => {
          console.error('[ERROR] Falha ao cancelar reserva:', err);
          throw err;
        })
      );
  }



}

// mais metodos
