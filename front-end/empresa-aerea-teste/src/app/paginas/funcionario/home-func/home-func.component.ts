import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VooService } from '../../../services/voo.service';

@Component({
  selector: 'app-home-func',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home-func.component.html',
  styleUrls: ['./home-func.component.css'],
})
export class HomeFuncComponent implements OnInit {
  voos: any[] = [];

  constructor(private vooService: VooService) {}

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

  confirmarEmbarque(codigo: string): void {
    console.log('Confirmação de embarque para o voo:', codigo);
    // Implementar lógica de confirmação (R12)
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
