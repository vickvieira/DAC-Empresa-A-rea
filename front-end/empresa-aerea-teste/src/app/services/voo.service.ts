import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Voo } from '../models/voo.model';
import { catchError, map, Observable, switchMap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class VooService {
  private apiUrl = 'http://localhost:3000/voos';

  constructor(private http: HttpClient) {}

  buscarVoos(origem: string, destino: string): Observable<Voo[]> {
    let queryParams = '';
    if (origem) {
      queryParams += `&origem=${origem}`;
    }
    if (destino) {
      queryParams += `&destino=${destino}`;
    }

    // sem dataHora no parâmetro... por enquanto
    return this.http.get<Voo[]>(`${this.apiUrl}?${queryParams}`);
  }

  getVooByCodigo(codigo: string): Observable<Voo> {
    return this.http
      .get<Voo[]>(`http://localhost:3000/voos?codigo=${codigo}`)
      .pipe(
        map((voos) => voos[0]) // Retorna o primeiro item do array
      );
  }

  //tava dando mt trabalho tentar filtrar os voos próximos do json, então pego todos e filtro no front msm
  getVoos(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:3000/voos');
  }

  cancelarVoo(codigoVoo: string): Observable<Voo> {
    return this.http.get<Voo[]>(`${this.apiUrl}?codigo=${codigoVoo}`).pipe(
      map((voos) => {
        const voo = voos[0]; // Assume que o código é único
        if (!voo) {
          throw new Error('Voo não encontrado.');
        }
        if (voo.status !== 'CONFIRMADO') {
          throw new Error(
            'O voo não pode ser cancelado, pois não está CONFIRMADO.'
          );
        }
        voo.status = 'CANCELADO'; // Atualiza o status do voo para CANCELADO
        return voo;
      }),
      switchMap((voo) => this.http.put<Voo>(`${this.apiUrl}/${voo.id}`, voo)),
      catchError((error) => {
        console.error('Erro ao cancelar voo:', error);
        return throwError(
          () => new Error(error.message || 'Erro desconhecido.')
        );
      })
    );
  }
}
