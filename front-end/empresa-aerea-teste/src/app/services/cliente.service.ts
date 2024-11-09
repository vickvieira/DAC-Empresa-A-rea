import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../models/cliente.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = 'http://localhost:3000/clientes';

  constructor(private http: HttpClient) {}

  // Método para buscar cliente pelo ID
  getClienteById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  getClienteByIdString(id: string):Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  // Método para buscar cliente pelo email
  getClienteByEmail(email: string): Observable<Cliente> {
    return this.http.get<Cliente[]>(`${this.apiUrl}?email=${email}`).pipe(
      // Retorna o primeiro cliente da lista de resultados
      map(clientes => clientes[0])
    );
  }

  // Adicionar novo cliente
  addCliente(usuario: any): Observable<any> {
    console.log(usuario);
    return this.http.post<any>(this.apiUrl, usuario);
  }

  // Outros métodos relacionados ao cliente
}