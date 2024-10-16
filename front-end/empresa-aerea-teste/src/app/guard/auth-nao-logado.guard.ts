import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Cliente } from '../models/cliente.model';

export class authNaoLogadoGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const cliente: Cliente | null = this.authService.getCliente();

    if (!cliente) {  // Se o cliente NÃO está logado
      return true;  
    } else {
      this.router.navigate(['/cliente']);  // Redireciona para a rota principal se o cliente estiver logado
      return false;  
    }
  }
}