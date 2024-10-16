import { Injectable } from '@angular/core';
import { ClienteService } from './cliente.service';
import { Cliente } from '../models/cliente.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private cliente: Cliente | null = null;

  constructor(private clienteService: ClienteService) {}

  // Método de login que busca cliente por email
  login(email: string): void {
    this.clienteService.getClienteByEmail(email).subscribe(
      (cliente: Cliente) => {
        console.log('é o cliente')
        console.log(cliente)
        localStorage.setItem('cliente', JSON.stringify(cliente));
      },
      error => {
        console.error('Erro ao buscar cliente:', error);
      }
    );
  }

  // Método para obter o cliente logado
  getCliente(): Cliente | null {
    console.log('dentro')
    console.log(this.cliente)
    if (this.cliente) {
      return this.cliente;
    } else {
      // Tenta recuperar o cliente do localStorage
      const storedCliente = localStorage.getItem('cliente');
      console.log('stored')
      console.log(storedCliente)
      if (storedCliente) {
        this.cliente = JSON.parse(storedCliente);
        return this.cliente;
      }
    }
    return null;
  }

  // Método de logout
  logout(): void {
    this.cliente = null;
  }
}