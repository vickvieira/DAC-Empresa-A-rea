import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Aeroporto } from '../models/aeroporto.model';

@Injectable({
  providedIn: 'root',
})
export class AeroportoService {
  private apiUrl = 'http://localhost:3000/aeroportos'; // URL da API para aeroportos

  constructor(private http: HttpClient) {}

  getAeroportoByCodigo(codigo: string): Observable<Aeroporto | null> {
    return this.http.get<Aeroporto[]>(`${this.apiUrl}?codigo=${codigo}`).pipe(
      map((aeroportos) => (aeroportos.length ? aeroportos[0] : null)) // Retorna o primeiro aeroporto ou null
    );
  }

  getAeroportoByNome(nome: string): Observable<Aeroporto | null> {
    return this.http.get<Aeroporto[]>(`${this.apiUrl}?nome=${nome}`).pipe(
      map((aeroportos) => (aeroportos.length ? aeroportos[0] : null)) // Retorna o primeiro aeroporto ou null
    );
  }
}
