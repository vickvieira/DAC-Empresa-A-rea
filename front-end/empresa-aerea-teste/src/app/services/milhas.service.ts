import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Milhas } from '../models/milhas.model';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MilhasService {
  private apiUrl = 'http://localhost:3000/milhas';

  constructor(private http: HttpClient) {}

  getMilhasByClienteId(clienteId: number): Observable<number> {
    return this.http.get<Milhas[]>(`${this.apiUrl}?clienteId=${clienteId}`)
      .pipe(
        map(milhasArray => milhasArray.length > 0 ? milhasArray[0].saldo : 0)
      );
  }
  atualizarMilhas(clienteId: number, milhasDelta: number): Observable<Milhas> {
    // Primeiro, obter o saldo atual de milhas do cliente
    return this.http.get<Milhas[]>(`${this.apiUrl}?clienteId=${clienteId}`).pipe(
      switchMap((milhasArray: Milhas[]) => {
        if (milhasArray.length === 0) {
          throw new Error('Milhas do cliente não encontradas');
        }

        const milhasCliente = milhasArray[0];
        const novoSaldo = milhasCliente.saldo + milhasDelta;

        // Atualizar o saldo no servidor
        const milhasAtualizadas: Milhas = { ...milhasCliente, saldo: novoSaldo };
        return this.http.put<Milhas>(`${this.apiUrl}/${milhasCliente.clienteId}`, milhasAtualizadas);
      })
    );
  }

  // mais metodos
}
