import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Milhas } from '../models/milhas.model';
import { ExtratoMilhas } from '../models/extrato-milhas.model';
import { Observable, of, pipe } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MilhasService {
  apiUrl = 'http://localhost:3000/milhas';
  apiUrlTransacoes = 'http://localhost:3000/transacoes_milhas';


  httpOption = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }


  getMilhasByClienteId(clienteId: number): Observable<number> {
    return this.httpClient.get<ExtratoMilhas[]>(`${this.apiUrl}?clienteId=${clienteId}`)
      .pipe(
        map(milhasArray => milhasArray.length > 0 ? milhasArray[0].saldo : 0)
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
    return this.httpClient.get<ExtratoMilhas[]>(`${this.apiUrlTransacoes}/?clienteId=${term}`).pipe(
      tap(x => x.length),

    );
  }


  getTodosExtratos(): Observable<ExtratoMilhas[]> {
    return this.httpClient.get<ExtratoMilhas[]>(this.apiUrlTransacoes, this.httpOption)
  }

  atualizarSaldo() {
    //saldo anterior menos operação nova

    //return this.httpClient.put<Milhas>
  }

  // mais metodos

}
