import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VooService } from '../../../services/voo.service';
import { MilhasService } from '../../../services/milhas.service';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../../services/reserva.service';

@Component({
  selector: 'app-efetuar-reserva',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './efetuar-reserva.component.html',
  styleUrls: ['./efetuar-reserva.component.css']
})
export class EfetuarReservaComponent implements OnInit {
  voo: any;
  saldoMilhas: number | null = null;
  quantidadePassagens = 1; // Valor padrão de uma passagem
  valorTotal: number = 0; // Valor total a pagar
  milhasUsadas: number = 0; // Milhas que o cliente deseja usar
  valorRestante: number | null = null; // Valor que precisa ser pago em dinheiro
  codigoReserva: string | null = null;
  milhasNecessarias: number = 0;
  maxMilhasUsadas: number = 0;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private vooService: VooService,
    private milhasService: MilhasService,
    private authService: AuthService,
    private reservaService: ReservaService
  ) {}

  ngOnInit(): void {
    const codigoVoo = this.route.snapshot.paramMap.get('codigo');
    const clienteId = this.authService.getClienteId();
    // this.calcularMaxMilhas();

    if (clienteId !== null && codigoVoo) {
      this.vooService.getVooByCodigo(codigoVoo).subscribe((voo) => {
        if (voo) {
          this.voo = voo; // Voo encontrado
          this.calcularValorTotal(); // Calcular o valor total inicial
        } else {
          console.error('Voo não encontrado.');
        }
      });

      // Buscar saldo de milhas do cliente
      this.milhasService.getMilhasByClienteId(clienteId).subscribe((saldo) => {
        this.saldoMilhas = saldo;
      });
    } else {
      console.error('Cliente não logado ou código de voo não encontrado.');
    }
  }

  calcularValorTotal(): void {
    if (this.voo) {
      this.valorTotal = this.voo.valorPassagem * this.quantidadePassagens;
      this.calcularMilhasNecessarias();// Calcula as milhas necessárias
      this.calcularValorRestante(); //recalcula
    }
  }

  // Calcular a quantidade de milhas necessárias com base no valor total
  calcularMilhasNecessarias(): void {
    if (this.voo) {
      // Regra: 10x o valor da passagem em milhas
      this.milhasNecessarias = this.valorTotal * 10;
    }
  }

  calcularValorRestante(): void {
    if (this.saldoMilhas !== null && this.milhasUsadas <= this.saldoMilhas) {
      const milhasDesconto = Math.min(this.milhasUsadas, this.valorTotal * 10); // Máximo de milhas aplicável
      this.valorRestante = this.valorTotal - (milhasDesconto / 10); // 10 milhas = R$ 1
    } else {
      this.valorRestante = this.valorTotal; // Se não houver milhas ou exceder o saldo, paga o valor total
    }
  }

  onQuantidadePassagensChange(): void {
    this.calcularValorTotal(); // Recalcula o valor total e o valor restante ao mudar a quantidade de passagens
  }

//   calcularMaxMilhas(): number {
//     this.maxMilhasUsadas = Math.min(this.saldoMilhas || 0, this.valorTotal * 10); // 10 é o fator de conversão
//     return this.maxMilhasUsadas;
// }
  onMilhasUsadasChange(): void {
    const maxMilhas = Math.min(this.saldoMilhas || 0, this.valorTotal * 10); // 10 é o fator de conversão
    if (this.milhasUsadas > maxMilhas) {
      this.errorMessage = `Você só pode usar até ${maxMilhas} milhas.`;
    } else {
      this.errorMessage = null; // Limpa a mensagem de erro se o valor estiver correto
    }
    this.calcularValorRestante(); // Recalcula o valor restante ao mudar a quantidade de milhas
  }


  // Confirmar a compra e gerar o código de reserva
  confirmarCompra(): void {
    const clienteId = this.authService.getClienteId();
    if (clienteId !== null) {
      this.reservaService.criarReserva(clienteId, this.voo, this.quantidadePassagens, this.valorTotal, this.milhasUsadas)
        .subscribe(() => console.log('Reserva criada com sucesso!'));
    }
  }
  

  // Função para gerar um código de reserva único (3 letras e 3 números)
  gerarCodigoReserva(): string {
    const letras = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const numeros = '0123456789';
    let codigo = '';
    for (let i = 0; i < 3; i++) {
      codigo += letras.charAt(Math.floor(Math.random() * letras.length));
    }
    for (let i = 0; i < 3; i++) {
      codigo += numeros.charAt(Math.floor(Math.random() * numeros.length));
    }
    return codigo;
  }
}
