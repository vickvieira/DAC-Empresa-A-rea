import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Milhas } from '../models/milhas.model';
import { ExtratoMilhas } from '../models/extrato-milhas.model';
import { Observable, of, pipe, throwError } from 'rxjs';
import { catchError, map, tap, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class MilhasService {
  apiUrl = 'http://localhost:3000/milhas';
  apiUrlTransacoes = 'http://localhost:3000/transacoes_milhas';

  httpOption = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {}

  getMilhasByClienteId(clienteId: number): Observable<number> {
    console.log('getMilhasByClienteId ' + clienteId);
    return this.http
      .get<Milhas[]>(`${this.apiUrl}?id=${clienteId}`)
      .pipe(
        map((milhasArray) =>
          milhasArray.length > 0 ? milhasArray[0].saldo : 0
        )
      );
  }

  atualizarMilhas(clienteId: number, milhasDelta: number): Observable<Milhas> {
    return this.http
      .get<Milhas[]>(`${this.apiUrl}?clienteId=${clienteId}`)
      .pipe(
        switchMap((milhasArray: Milhas[]) => {
          if (milhasArray.length === 0) {
            return throwError('Milhas do cliente não encontradas');
          }

          const milhasCliente = milhasArray[0];
          const novoSaldo = milhasCliente.saldo + milhasDelta;

          const milhasAtualizadas: Milhas = {
            ...milhasCliente,
            saldo: novoSaldo,
          };
          return this.http.put<Milhas>(
            `${this.apiUrl}/${milhasCliente.id}`,
            milhasAtualizadas
          );
        })
      );
  }

  atualizarMilhasString(
    clienteId: string,
    milhasDelta: number
  ): Observable<Milhas> {
    // Primeiro, obter o saldo atual de milhas do cliente
    return this.http
      .get<Milhas[]>(`${this.apiUrl}?clienteId=${clienteId}`)
      .pipe(
        switchMap((milhasArray: Milhas[]) => {
          if (milhasArray.length === 0) {
            throw new Error('Milhas do cliente não encontradas');
          }

          const milhasCliente = milhasArray[0];
          const novoSaldo = milhasCliente.saldo + milhasDelta;

          // Atualizar o saldo no servidor
          const milhasAtualizadas: Milhas = {
            ...milhasCliente,
            saldo: novoSaldo,
          };
          return this.http.put<Milhas>(
            `${this.apiUrl}/${milhasCliente.id}`,
            milhasAtualizadas
          );
        })
      );
  }

  /*
  getMilhasByClienteId(clienteId: number): Observable<number> {
    //return this.httpClient.get<number>(this.apiUrl+ "/"+ clienteId);
    return this.httpClient.get<number>(this.apiUrl);
  }
  */

  //precisa arrumar esse aqui ainda

  private log(message: string) {
    console.log(`erro: ${message}`);
  }

  getExtratoPorClienteId(term: string): Observable<ExtratoMilhas[]> {
    if (!term.trim()) {
      return of([]);
    }
    return this.http
      .get<ExtratoMilhas[]>(`${this.apiUrlTransacoes}/?clienteId=${term}`)
      .pipe(tap((x) => x.length));
  }

  getTodosExtratos(): Observable<ExtratoMilhas[]> {
    return this.http.get<ExtratoMilhas[]>(
      this.apiUrlTransacoes,
      this.httpOption
    );
  }

  /*
  atualizarSaldo(transacaoMaisRecente: ExtratoMilhas) {
    //saldo anterior menos operação nova
    let body: Milhas;
    body.saldo = transacaoMaisRecente.saldo;    
    return this.http.put<Milhas[]>(this.apiUrl,)
  }
  */

  atualizarSaldoMilhas(novoSaldo: Milhas): Observable<Milhas> {
    console.log('atualizar stringfy' + JSON.stringify(novoSaldo));
    console.log('url:' + this.apiUrl + '/' + novoSaldo.id);

    //return this.http.put<Milhas>(this.apiUrl + "/" + novoSaldo.id,
    //JSON.stringify(novoSaldo), this.httpOption)

    return this.http.put<Milhas>(
      this.apiUrl + '/' + novoSaldo.id,
      JSON.stringify(novoSaldo),
      this.httpOption
    );
  }

  getMilhasByClienteIdString(clienteId: string): Observable<number> {
    return this.http
      .get<ExtratoMilhas[]>(`${this.apiUrl}?clienteId=${clienteId}`)
      .pipe(
        map((milhasArray) =>
          milhasArray.length > 0 ? milhasArray[0].saldo : 0
        )
      );
  }

  inserirCompraMilhas(extratoMilhas: ExtratoMilhas): Observable<ExtratoMilhas> {
    return this.http.post<ExtratoMilhas>(
      this.apiUrlTransacoes,
      JSON.stringify(extratoMilhas),
      this.httpOption
    );
  }

  /*  
  getIdByClienteId(clienteId: string): Observable<string> {
    return this.http.get<Milhas[]>(`${this.apiUrl}?clienteId=${clienteId}`)
    .pipe(
      map(milhasArray => milhasArray.length > 0 ? milhasArray[0].saldo : 0)
      );
      }
      */
  // mais metodos

  //reembolso de milhas para o caso de cancelamento do voo,
  //mas talvez seria o caso de centralizar o reembolso do cancelamento de reserva pelo cliente aqui tb
  reembolsarMilhas(
    clienteId: number,
    descricao: string,
    quantidade: number
  ): Observable<any> {
    if (quantidade <= 0) {
      console.log(
        `[DEBUG] Nenhuma milha para reembolsar. Cliente: ${clienteId}`
      );
      return of(null); // Retorna um Observable vazio para evitar erros
    }

    console.log(
      `[DEBUG] Reembolsando ${quantidade} milhas para o cliente ${clienteId}. Descrição: ${descricao}`
    );

    // Atualiza o saldo de milhas do cliente
    return this.atualizarMilhas(clienteId, quantidade).pipe(
      switchMap((milhasAtualizadas) => {
        console.log(`[DEBUG] Saldo atualizado:`, milhasAtualizadas);

        // Registra a operação no extrato
        const extrato: ExtratoMilhas = {
          clienteId: clienteId.toString(),
          dataHora: new Date().toISOString(),
          saldo: milhasAtualizadas.saldo, // Novo saldo após a operação
          operacao: quantidade, // Valor positivo porque é um reembolso
          descricao: descricao,
        };

        return this.inserirCompraMilhas(extrato).pipe(
          tap(() =>
            console.log(
              `[DEBUG] Reembolso registrado no extrato para o cliente ${clienteId}.`
            )
          )
        );
      }),
      catchError((error) => {
        console.error(
          `[ERROR] Falha ao reembolsar milhas para o cliente ${clienteId}:`,
          error
        );
        return throwError(() => new Error('Erro ao reembolsar milhas.'));
      })
    );
  }
}
