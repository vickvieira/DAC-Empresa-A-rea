import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VooService } from '../../../services/voo.service';
import { MilhasService } from '../../../services/milhas.service';
import { CommonModule, NgIf } from '@angular/common';

@Component({
  selector: 'app-efetuar-reserva',
  standalone: true,
  imports: [NgIf, CommonModule],
  templateUrl: './efetuar-reserva.component.html',
  styleUrl: './efetuar-reserva.component.css'
})
export class EfetuarReservaComponent implements OnInit {
    voo: any;
    saldoMilhas: number | null = null;
    clienteId = 1; // Substituir AQUI pelo ID real do cliente autenticado
  
    constructor(
      private route: ActivatedRoute,
      private vooService: VooService,
      private milhasService: MilhasService
    ) {}
  
    ngOnInit(): void {
      const codigoVoo = this.route.snapshot.paramMap.get('codigo');
      const clienteId = this.route.snapshot.paramMap.get('id'); 
  
      if (codigoVoo) {
        this.vooService.getVooByCodigo(codigoVoo).subscribe((voo) => {
          if (voo) {
            this.voo = voo; // já foi feito o map do array no serviço, p/ q apareça um unico voo
          } else {
            console.error('Voo não encontrado.');
          }
        });
      }
      if (clienteId) {
        this.milhasService.getMilhasByClienteId(+clienteId).subscribe((saldo) => {
          this.saldoMilhas = saldo;
        });
      }
  
      this.milhasService.getMilhasByClienteId(this.clienteId).subscribe((saldo) => {
        this.saldoMilhas = saldo;
      });
    }
  }