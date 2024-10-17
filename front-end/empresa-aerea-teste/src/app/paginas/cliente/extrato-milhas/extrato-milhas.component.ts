import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { MilhasService } from '../../../services/milhas.service';
import { Cliente } from '../../../models/cliente.model';
import { ActivatedRoute } from '@angular/router';
import { ExtratoMilhas } from '../../../models/extrato-milhas.model';



@Component({
  selector: 'app-extrato-milhas',
  //standalone: true,
  templateUrl: './extrato-milhas.component.html',
  styleUrl: './extrato-milhas.component.css'
})
export class ExtratoMilhasComponent implements OnInit {
  cliente: Cliente | undefined;
  milhasSaldo: number = 0;
  extratoMilhas: ExtratoMilhas[] = [];

  constructor(
    private clienteService: ClienteService,
    private milhasService: MilhasService,

    //private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    //const clienteId = Number(this.route.snapshot.paramMap.get('id'));
    this.checaCliente();
    this.getMilhasSaldo(1);

    //this.listarTodosExtratos();
    //this.listarTodosExtratoByCliente(1);
  }

  checaCliente() {
    let clienteId = 1;

    this.getCliente(clienteId);
    //this.getMilhasSaldo(clienteId);
    this.listarTodosExtratoByCliente(clienteId);


    if (this.cliente) { //ve se o id do cliente foi capturado corretamente
      console.log('Cliente ID:' + this.cliente);
      //this.listarTodosExtratos();
      this.listarTodosExtratoByCliente(clienteId);
      this.getMilhasSaldo(clienteId);

    } else {
      console.error('Cliente ID não encontrado na rota VALOR CLIENTE:' + this.cliente);
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

  getSomaDoSaldoPeloExtrato(clienteId: number): void {
    this.listarTodosExtratoByCliente(clienteId);
  }

  
  listarTodosExtratoByCliente(clienteId: number): ExtratoMilhas[] {
    this.milhasService.getExtratoPorClienteId(String(clienteId)).subscribe({
      next: (data: ExtratoMilhas[]) => {
        if (data == null) {
          this.extratoMilhas = [];
        }
        else {
          //data.saldo = 
          //data[1].saldo
          this.extratoMilhas = data;
          /*
          for (let i = 1; i < data.length; i++) {
            //this.extratoMilhas[i].saldo =+ this.extratoMilhas[i+1].saldo;
          }
          */
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

