import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VooService } from '../../../services/voo.service';
import { MilhasService } from '../../../services/milhas.service';
import { CommonModule, NgIf } from '@angular/common';
import { AuthService } from '../../../services/auth.service';

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
    // clienteId = 1; // Substituir AQUI pelo ID real do cliente autenticado
  
    constructor(
      private route: ActivatedRoute,
      private vooService: VooService,
      private milhasService: MilhasService,
      private authService: AuthService
    ) {}
  
    ngOnInit(): void {
      const codigoVoo = this.route.snapshot.paramMap.get('codigo');
      const cliente = this.authService.getClienteId();
  
      if (cliente && codigoVoo) {
        this.vooService.getVooByCodigo(codigoVoo).subscribe((voo) => {
          if (voo) {
            this.voo = voo; // Voo encontrado
          } else {
            console.error('Voo não encontrado.');
          }
        });
  
        // Buscar saldo de milhas do cliente
        this.milhasService.getMilhasByClienteId(cliente).subscribe((saldo) => {
          this.saldoMilhas = saldo;
        });
      } else {
        console.error('Cliente não logado ou código de voo não encontrado.');
      }
    }
  }