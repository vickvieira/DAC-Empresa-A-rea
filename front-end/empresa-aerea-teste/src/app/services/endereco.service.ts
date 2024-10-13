import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../models/cliente.model';
import { Observable } from 'rxjs';
import { Endereco } from '../models/endereco.models';

@Injectable({
  providedIn: 'root'
})
export class EnderecoService {
  private apiUrl = 'http://localhost:3000/endereco';

  constructor(private http: HttpClient) {}

  getClienteById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  addEndereco(endereco: Endereco): Observable<Endereco>{
    console.log(endereco)
    return this.http.post<any>(this.apiUrl, endereco);
  }
  // Outros métodos relacionados ao cliente
}
