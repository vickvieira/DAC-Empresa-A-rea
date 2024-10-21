import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ClienteService } from '../../services/cliente.service';
import { MilhasService } from '../../services/milhas.service';
import { Cliente } from '../../models/cliente.model';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  cliente: Cliente | undefined;
  milhasSaldo: number = 0;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private clienteService: ClienteService,
    private milhasService: MilhasService,) {}

    ngOnInit(): void {
      const clienteId = this.authService.getCliente();
      console.log(clienteId)
      if (clienteId && clienteId.id) {
        this.getCliente(clienteId.id);
        this.getMilhasSaldo(clienteId.id);
        console.log('Cliente ID capturado nav-bar:', clienteId.id);
      } else {
        console.error('Cliente ID não encontrado no serviço de autenticação');
      }
    }


  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  getCliente(id: number): void {
    this.clienteService.getClienteById(id).subscribe((data: Cliente) => {
      this.cliente = data;
    });
  }

  getMilhasSaldo(clienteId: number): void {
    this.milhasService.getMilhasByClienteId(clienteId).subscribe((saldo: number) => {
      this.milhasSaldo = saldo;
    });
  }
}
