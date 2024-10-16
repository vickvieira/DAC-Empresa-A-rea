import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Cliente } from '../models/cliente.model';

@Injectable({
  providedIn: 'root'
})
export class authNaoLogadoGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const cliente: Cliente | null = this.authService.getCliente();

    if (!cliente) {  // Se o cliente NÃO está logado
      return true; 
    } else {
      return false; 
    }
  }
}