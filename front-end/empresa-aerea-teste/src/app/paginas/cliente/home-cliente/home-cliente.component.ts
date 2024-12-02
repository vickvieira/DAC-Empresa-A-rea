import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { MilhasService } from '../../../services/milhas.service';
import { ReservaService } from '../../../services/reserva.service';
import { Cliente } from '../../../models/cliente.model';
import { Reserva } from '../../../models/reserva.model';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CancelarReservaComponent } from '../cancelar-reserva/cancelar-reserva.component';
import { ComprarMilhasComponent } from '../comprar-milhas/comprar-milhas.component';

@Component({
  selector: 'app-home-cliente',
  templateUrl: './home-cliente.component.html',
  styleUrls: ['./home-cliente.component.css'],
  standalone: true,
  imports: [CommonModule,],
})
export class HomeClienteComponent implements OnInit {
  cliente: Cliente | undefined;
  milhasSaldo: number = 0;
  reservas: Reserva[] = [];
  reservasFiltradas: Reserva[] = [];
  clienteId: number | null = null;
  expandedReserva: Reserva | null = null;

  constructor(
    private clienteService: ClienteService,
    private milhasService: MilhasService,
    private reservaService: ReservaService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    // Verifica se o usuário está logado
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']); // Redireciona para a página de login se não estiver logado
      return;
    }

    // Se o usuário estiver logado, pega o clienteId do AuthService
    this.clienteId = this.authService.getClienteId();
    if (this.clienteId) {
      this.getCliente(this.clienteId);
      this.getMilhasSaldo(this.clienteId);
      this.getReservas(this.clienteId);
      this.fetchReservas();
    } else {
      this.router.navigate(['/login']); // Caso o ID do cliente não seja encontrado, redireciona para login
    }
  }

  fetchReservas(): void {       //Filtra as reservas por cliente e ordena por data e hora
    this.http.get<Reserva[]>('http://localhost:3000/reservas').subscribe(reservas => {
      this.reservas = reservas;
      if (this.clienteId) {
        this.reservasFiltradas = this.reservas
          .filter(reserva => reserva.clienteId === this.clienteId)
          .sort((a, b) => new Date(b.voo.dataHora).getTime() - new Date(a.voo.dataHora).getTime());
      } else {
        this.reservasFiltradas = [];
        console.error('Cliente ID não encontrado');
      }
      console.log('Reservas filtradas:', this.reservasFiltradas);
    });
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

  getReservas(clienteId: number): void {
    this.reservaService.getReservasByClienteId(clienteId).subscribe((data: Reserva[]) => {
      this.reservas = data;
    });
  }

  modalCancelarReserva(reserva: Reserva ){
    const modalRef = this.modalService.open(CancelarReservaComponent);
    modalRef.componentInstance.reserva = reserva;
  }

  cancelarReserva(reserva: Reserva): void {
    console.log("codigo cancelar reserva: " + reserva.id);
    //this.reservaService.cancelarReserva(id).subscribe(() => {
    //this.reservas = this.reservas.filter(reserva => reserva.codigo !== codigo);
    /*
    this.reservaService.cancelarReserva(reserva).subscribe({
      complete: () => { this.getReservas(1); }
    });
    */

    this.reservaService.cancelarReserva(reserva).subscribe({
      next: response => {
        console.log('REserva cancelada com sucesso ??????:', response);
      }
    });

  };


  toggleVisualizar(reserva: Reserva) {
    console.log('Reserva expandida atual:', reserva);
    console.log('Reserva expandida anterior:', this.expandedReserva);
    this.expandedReserva = this.expandedReserva === reserva ? null : reserva;
  }
}
