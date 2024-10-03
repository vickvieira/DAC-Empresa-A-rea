import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { MilhasService } from '../../../services/milhas.service';
import { Cliente } from '../../../models/cliente.model';
import { ActivatedRoute } from '@angular/router';
import { Milhas } from '../../../models/milhas.model';


@Component({
  selector: 'app-extrato-milhas',
  //standalone: true,
  templateUrl: './extrato-milhas.component.html',
  styleUrl: './extrato-milhas.component.css'
})
export class ExtratoMilhasComponent implements OnInit {
  cliente: Cliente | undefined;
  milhasSaldo: number = 0;
  extratoMilhas: Milhas[] = [];


  constructor(
    private clienteService: ClienteService,
    private milhasService: MilhasService,

    //private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    //const clienteId = Number(this.route.snapshot.paramMap.get('id'));
    this.checaCliente();
    this.listarTodosExtratos();
  }

  checaCliente() {
    let clienteId = 1;

    this.getCliente(clienteId);
    this.getMilhasSaldo(clienteId);

    if (this.cliente) { //ve se o id do cliente foi capturado corretamente
      console.log('Cliente ID:' + this.cliente);
      //this.listarTodosExtratos();
      this.listarTodosExtratoByCliente(clienteId);
      this.getMilhasSaldo(clienteId);
    } else {
      console.error('Cliente ID não encontrado na rota asdsasas');
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
  getMilhasExtrato(clienteId: number) {
    this.milhasService.getExtratobyClienteId(clienteId).subscribe((extrato: Milhas[]) => {
      this.extratoMilhas = extrato;
    });
  }
  */
  listarTodosExtratoByCliente(clienteId: number): Milhas[] {
    this.milhasService.getExtratobyClienteId(clienteId).subscribe({
      next: (data: Milhas[]) => {
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

  listarTodosExtratos(): Milhas[] {
    this.milhasService.getTodosExtratos().subscribe({
      next: (data: Milhas[]) => {
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

