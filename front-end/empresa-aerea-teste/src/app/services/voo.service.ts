import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Voo } from '../models/voo.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VooService {
  getVoos() {
    throw new Error('Method not implemented.');
  }

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

  // Adiciona dataHora_gte para garantir que a busca traga apenas voos futuros
  return this.http.get<Voo[]>(`${this.apiUrl}?dataHora_gte=${new Date().toISOString()}${queryParams}`);
}
}
