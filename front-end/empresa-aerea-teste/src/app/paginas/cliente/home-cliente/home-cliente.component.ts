import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { MilhasService } from '../../../services/milhas.service';
import { ReservaService } from '../../../services/reserva.service';
import { Cliente } from '../../../models/cliente.model';
import { Reserva } from '../../../models/reserva.model';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home-cliente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home-cliente.component.html',
  styleUrls: ['./home-cliente.component.css']
})
export class HomeClienteComponent implements OnInit {
  cliente: Cliente | undefined;
  reservas: Reserva[] = [];
  milhasSaldo: number = 0;

  constructor(
    private clienteService: ClienteService,
    private milhasService: MilhasService,
    private reservaService: ReservaService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    const clienteId = Number(this.route.snapshot.paramMap.get('id'));
    this.getCliente(clienteId);
    this.getMilhasSaldo(clienteId);
    this.getReservas(clienteId);
    if (this.cliente) { //ve se o id do cliente foi capturado corretamente
      console.log('Cliente ID:', this.cliente);
    } else {
      console.error('Cliente ID não encontrado na rota');
    }
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
}
