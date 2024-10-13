import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule, NgModel } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  clienteId: number | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    if (this.clienteId !== null) {
      this.authService.login(this.clienteId);
      this.router.navigate(['/cliente']); // Redirecionar para a página principal do cliente após o login
    } else {
      alert('Por favor, insira um ID de cliente válido.');
    }
  }
}
