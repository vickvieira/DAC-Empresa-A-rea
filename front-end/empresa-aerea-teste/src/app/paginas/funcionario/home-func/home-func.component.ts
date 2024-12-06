import { Component, NgModule, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VooService } from '../../../services/voo.service';
import { ReservaService } from '../../../services/reserva.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home-func',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home-func.component.html',
  styleUrls: ['./home-func.component.css'],
})
export class HomeFuncComponent implements OnInit {
  voos: any[] = [];
  codigoReserva: string = ''; // Armazena o código da reserva digitado
  codigoVooAtual: string = '';

  constructor(
    private vooService: VooService,
    private reservaService: ReservaService
  ) {}

  ngOnInit(): void {
    const dataInicial = new Date();
    const dataFinal = new Date(dataInicial.getTime() + 48 * 60 * 60 * 1000); // 48 horas adiante
    //faço o get de todos os voos e depois filtro os das últimas 48hrs direto no front... não é o ideal mas tava dando muito BO tentar filtrar do json-server
    this.vooService.getVoos().subscribe((voos) => {
      this.voos = voos
        .filter((voo: any) => {
          const dataVoo = new Date(voo.dataHora);
          return dataVoo >= dataInicial && dataVoo <= dataFinal;
        })
        .sort(
          (a, b) =>
            new Date(a.dataHora).getTime() - new Date(b.dataHora).getTime()
        );
    });
  }

  abrirModal(codigoVoo: string): void {
    this.codigoVooAtual = codigoVoo; // Define o voo atual para a confirmação
  }

  confirmarEmbarque(): void {
    console.log('Código da reserva:', this.codigoReserva);
    if (!this.codigoReserva || !this.codigoVooAtual) {
      alert('Por favor, insira o código da reserva.');
      return;
    }

    this.reservaService
      .confirmarEmbarque(this.codigoReserva, this.codigoVooAtual)
      .subscribe({
        next: () => {
          alert('Embarque confirmado com sucesso!');
          this.codigoReserva = ''; // Reseta o campo de entrada
          this.codigoVooAtual = ''; // Reseta o voo atual
          const modalElement = document.getElementById(
            'confirmarEmbarqueModal'
          );
          if (modalElement) (modalElement as any).classList.remove('show'); // Fecha o modal
        },
        error: (err) => {
          alert(`Erro: ${err.message}`);
        },
      });
  }

  cancelarVoo(codigo: string): void {
    console.log('Cancelamento do voo:', codigo);
    // Implementar lógica de cancelamento (R13)
  }

  realizarVoo(codigo: string): void {
    console.log('Realização do voo:', codigo);
    // Implementar lógica de realização (R14)
  }
}
