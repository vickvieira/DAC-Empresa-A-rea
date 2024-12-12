import { Component, NgModule, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VooService } from '../../../services/voo.service';
import { ReservaService } from '../../../services/reserva.service';
import { FormsModule } from '@angular/forms';
import { MilhasService } from '../../../services/milhas.service';
import { NavbarFuncComponent } from '../../../componentes/navbar-func/navbar-func.component';

@Component({
  selector: 'app-home-func',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarFuncComponent],
  templateUrl: './home-func.component.html',
  styleUrls: ['./home-func.component.css'],
})
export class HomeFuncComponent implements OnInit {
  voos: any[] = [];
  codigoReserva: string = ''; // Armazena o código da reserva digitado
  codigoVooAtual: string = '';

  constructor(
    private vooService: VooService,
    private reservaService: ReservaService,
    private milhasService: MilhasService
  ) {}

  ngOnInit(): void {
    const dataInicial = new Date();
    const dataFinal = new Date(dataInicial.getTime() + 48 * 60 * 60 * 1000); // 5000 horas adiante
    //faço o get de todos os voos e depois filtro os das últimas 5000hrs direto no front... não é o ideal mas tava dando muito BO tentar filtrar do json-server
    this.vooService.getVoos().subscribe((voos) => {
      console.log(voos);
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

  cancelarVoo(codigoVoo: string): void {
    console.log(`[DEBUG] Iniciando cancelamento do voo: ${codigoVoo}`);

    if (confirm('Tem certeza de que deseja cancelar este voo?')) {
      this.vooService.cancelarVoo(codigoVoo).subscribe({
        next: () => {
          console.log(`[DEBUG] Voo ${codigoVoo} cancelado com sucesso.`);

          this.reservaService
            .atualizarReservasParaCancelamento(codigoVoo)
            .subscribe({
              next: (reservasAtualizadas) => {
                reservasAtualizadas.forEach((reserva) => {
                  if (reserva.milhasUtilizadas > 0) {
                    this.milhasService
                      .reembolsarMilhas(
                        Number(reserva.clienteId),
                        `Cancelamento do voo ${codigoVoo}`,
                        reserva.milhasUtilizadas
                      )
                      .subscribe({
                        next: () =>
                          console.log(
                            `[DEBUG] Milhas reembolsadas para a reserva ${reserva.codigo}.`
                          ),
                        error: (err) =>
                          console.error(
                            `[ERROR] Falha ao reembolsar milhas para a reserva ${reserva.codigo}:`,
                            err
                          ),
                      });
                  }
                });

                console.log(
                  `[DEBUG] Reservas associadas ao voo ${codigoVoo} atualizadas.`
                );
                alert(
                  'Voo e reservas associadas cancelados com sucesso! Milhas foram reembolsadas.'
                );
                this.ngOnInit(); // Recarrega a lista de voos
              },
              error: (err) => {
                console.error(
                  `[ERROR] Falha ao atualizar reservas para o voo ${codigoVoo}:`,
                  err
                );
                alert(`Erro ao cancelar reservas: ${err.message}`);
              },
            });
        },
        error: (err) => {
          console.error(`[ERROR] Falha ao cancelar o voo ${codigoVoo}:`, err);
          alert(`Erro ao cancelar voo: ${err.message}`);
        },
      });
    }
  }

  realizarVoo(codigoVoo: string): void {
    console.log(`[DEBUG] Iniciando realização do voo: ${codigoVoo}`);

    if (confirm('Tem certeza de que deseja marcar este voo como realizado?')) {
      this.vooService.realizarVoo(codigoVoo).subscribe({
        next: () => {
          console.log(`[DEBUG] Voo ${codigoVoo} realizado com sucesso.`);

          this.reservaService
            .atualizarReservasParaRealizacao(codigoVoo)
            .subscribe({
              next: (reservasAtualizadas) => {
                console.log(
                  `[DEBUG] Reservas atualizadas para o voo ${codigoVoo}:`,
                  reservasAtualizadas
                );
                alert(
                  'Voo e reservas associadas marcados como realizados com sucesso!'
                );
                this.ngOnInit(); // Recarrega a lista de voos
              },
              error: (err) => {
                alert(`Erro ao atualizar reservas: ${err.message}`);
              },
            });
        },
        error: (err) => {
          alert(`Erro ao realizar voo: ${err.message}`);
        },
      });
    }
  }
}
