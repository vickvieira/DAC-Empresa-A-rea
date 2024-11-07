import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { MilhasService } from '../../../services/milhas.service';
import { ReservaService } from '../../../services/reserva.service';
import { Cliente } from '../../../models/cliente.model';
import { Reserva } from '../../../models/reserva.model';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home-cliente',
  templateUrl: './home-cliente.component.html',
  styleUrls: ['./home-cliente.component.css'],
  standalone: true,
  imports: [CommonModule]

})
export class HomeClienteComponent implements OnInit {
  cliente: Cliente | undefined;
  milhasSaldo: number = 0;
  reservas: Reserva[] = [];
  reservasFiltradas: Reserva[] = [];
  clienteId: number | undefined;
  expandedReserva: Reserva | null = null;
  reserva: any;

  constructor(
    private clienteService: ClienteService,
    private milhasService: MilhasService,
    private reservaService: ReservaService,
    private route: ActivatedRoute,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.clienteId = Number(this.route.snapshot.paramMap.get('id'));
    this.getCliente(this.clienteId);
    this.getMilhasSaldo(this.clienteId);
    this.getReservas(this.clienteId);
    this.fetchReservas();

  }
  fetchReservas(): void {
    this.http.get<Reserva[]>('http://localhost:3000/reservas').subscribe(reservas => {
      this.reservas = reservas;
      if (this.cliente) { //verifica se o id do cliente foi capturado corretamente
        console.log('Cliente ID:', this.cliente);
        this.reservasFiltradas = this.reservas.filter(reserva => reserva.clienteId === this.clienteId);
      } else {
        this.reservasFiltradas = []; // Se não houver clienteId, define como array vazio
        console.error('Cliente ID não encontrado na rota');
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

  cancelarReserva(codigo: string): void {
    this.reservaService.cancelarReserva(codigo).subscribe(() => {
      this.reservas = this.reservas.filter(reserva => reserva.codigo !== codigo);
    });
  }

  toggleVisualizar(reserva: Reserva) {
    console.log('Reserva expandida atual:', reserva);
    console.log('Reserva expandida anterior:', this.expandedReserva);
    this.expandedReserva = this.expandedReserva === reserva ? null : reserva;
  }


}
