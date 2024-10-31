import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VooService } from '../../../services/voo.service';

@Component({
  selector: 'app-home-func',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home-func.component.html',
  styleUrls: ['./home-func.component.css']
})
export class HomeFuncComponent implements OnInit {
  voos: any[] = [];

  constructor(private vooService: VooService) {}

  ngOnInit(): void {
    const dataInicial = new Date();
    const dataFinal = new Date(dataInicial.getTime() + 48 * 60 * 60 * 1000); // 48 horas adiante

    this.vooService.getVoosProximos(dataInicial, dataFinal).subscribe((voos) => {
      this.voos = voos.sort((a, b) => new Date(a.dataHora).getTime() - new Date(b.dataHora).getTime());
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
