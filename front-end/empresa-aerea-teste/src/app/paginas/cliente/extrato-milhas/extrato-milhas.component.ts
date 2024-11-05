import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { MilhasService } from '../../../services/milhas.service';
import { Cliente } from '../../../models/cliente.model';
import { ActivatedRoute } from '@angular/router';
import { ExtratoMilhas } from '../../../models/extrato-milhas.model';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { AuthService } from '../../../services/auth.service';




@Component({
  selector: 'app-extrato-milhas',
  standalone: true,
  imports: [NgFor, NgIf, DatePipe],
  templateUrl: './extrato-milhas.component.html',
  styleUrl: './extrato-milhas.component.css'
})
export class ExtratoMilhasComponent implements OnInit {
  cliente: Cliente | undefined;
  milhasSaldo: number = 0;
  extratoMilhas: ExtratoMilhas[] = [];
  cliente2!: Cliente | null;

  constructor(
    private clienteService: ClienteService,
    private milhasService: MilhasService,
    private authService: AuthService,

    //private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.cliente2 = this.authService.getCliente();
    if (this.cliente2 && this.cliente2.id) {
      console.log(this.cliente2);
      this.listarTodosExtratoByCliente(this.cliente2.id.toString());
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

  /*
  getSomaDoSaldoPeloExtrato(clienteId: number): void {
    this.listarTodosExtratoByCliente(clienteId);
  }
  */

  listarTodosExtratoByCliente(clienteId: string): ExtratoMilhas[] {
    this.milhasService.getExtratoPorClienteId(String(clienteId)).subscribe({
      next: (data: ExtratoMilhas[]) => {
        if (data == null) {
          this.extratoMilhas = [];
        }
        else {
          this.extratoMilhas = data;
        }
      }
    });
    return this.extratoMilhas;
  }

  calcularSaldoNoExtrato() {

  }


  listarTodosExtratos(): ExtratoMilhas[] {
    this.milhasService.getTodosExtratos().subscribe({
      next: (data: ExtratoMilhas[]) => {
        if (data == null) {
          this.extratoMilhas = [];
        }
        else {
          this.extratoMilhas = data;

        }
      }
    });
    return this.extratoMilhas;
  }
}

