import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private clienteId: number | null = null;

  constructor() {}

  login(clienteId: number): void {
    this.clienteId = clienteId;
  }

  getClienteId(): number | null {
    return this.clienteId;
  }

  logout(): void {
    this.clienteId = null;
  }
}
