import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Cliente } from '../models/cliente.model';

@Injectable({
  providedIn: 'root'
})
export class authClienteGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const cliente: Cliente | null = this.authService.getCliente();

    if (cliente && cliente.tipo === 'C') {  // Verifica se o cliente está logado e é do tipo "C"
      return true;  // Permite acesso
    } else {
      this.router.navigate(['/login']);  // Redireciona para a página de login se não for do tipo "C"
      return false;  // Bloqueia acesso
    }
  }
}