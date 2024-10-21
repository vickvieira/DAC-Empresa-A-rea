import { Injectable } from '@angular/core';
import { ClienteService } from './cliente.service';
import { Cliente } from '../models/cliente.model';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private cliente: Cliente | null = null;

  constructor(private clienteService: ClienteService) {}

  // Método de login que busca cliente por email
  login(email: string): Observable<Cliente> {
    return this.clienteService.getClienteByEmail(email).pipe(
      tap((cliente: Cliente) => {
        localStorage.setItem('cliente', JSON.stringify(cliente));
        this.cliente = cliente; // Armazena o cliente em memória
      })
    );
  }

  // Método para obter o cliente logado
  getCliente(): Cliente | null {
    if (this.cliente) {
      return this.cliente;
    } else {
      // Tenta recuperar o cliente do localStorage
      const storedCliente = localStorage.getItem('cliente');
      if (storedCliente) {
        this.cliente = JSON.parse(storedCliente);
        return this.cliente;
      }
    }
    return null;
  }

  isLoggedIn(): boolean {
    return this.getCliente() !== null; // Retorna true se houver um cliente válido
  }

  // Método de logout
  logout(): void {
    this.cliente = null;
    localStorage.removeItem('cliente'); // Limpa o cliente do localStorage
  }
}