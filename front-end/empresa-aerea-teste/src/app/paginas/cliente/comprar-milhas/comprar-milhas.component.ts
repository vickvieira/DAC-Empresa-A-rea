import { Component } from '@angular/core';
import { MilhasService } from '../../../services/milhas.service';
import { ClienteService } from '../../../services/cliente.service';
import { ExtratoMilhas } from '../../../models/extrato-milhas.model';
import { Router } from '@angular/router';
import { Cliente } from '../../../models/cliente.model';
import { AuthService } from '../../../services/auth.service';
import { Milhas } from '../../../models/milhas.model';

@Component({
  selector: 'app-comprar-milhas',
  standalone: true,
  imports: [],
  templateUrl: './comprar-milhas.component.html',
  styleUrl: './comprar-milhas.component.css'
})
export class ComprarMilhasComponent {
  valorNovo!: ExtratoMilhas;
  isLoading: boolean = false;
  cliente!: Cliente | null;
  milhasSaldo: number = 0;
  idDoBDMilhas: string = "";
  milhas: Milhas = { id: "", saldo: 0 };
  constructor(
    private clienteService: ClienteService,
    private milhasService: MilhasService,
    private authService: AuthService,
    private router: Router,


    //private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.isLoading = false;
    console.log(this.valorNovo);
    this.cliente = this.authService.getCliente();
    if (this.cliente && this.cliente.id) {
      this.getMilhasSaldo(this.cliente.id);
    }
  }

  getDadosForm(): number {
    return parseFloat((<HTMLInputElement>document.getElementById("compra")).value);
  }

  montaDadosCompra(): ExtratoMilhas {
    this.cliente = this.authService.getCliente();

    //esse if é pra nao dar problema com o undefined
    if (this.cliente && this.cliente.id) {
      this.getMilhasSaldo(this.cliente.id);
      this.valorNovo = {
        clienteId: this.cliente.id?.toString(),
        dataHora: (new Date().toISOString()),
        operacao: this.getDadosForm(),
        saldo: this.calcularValorDoSaldoNoExtrato(this.milhasSaldo),
        descricao: "compra de milhas"
      }
    }
    console.log("Resultado montaDadosCompra: ", this.valorNovo)
    return this.valorNovo;
  }

  getMilhasSaldo(clienteId: any): void {
    this.milhasService.getMilhasByClienteId(clienteId).subscribe((saldo: number) => {
      this.milhasSaldo = saldo;
    });
  }

  comprarMilhas(): void {
    this.isLoading = true;
    let extrato: ExtratoMilhas = this.montaDadosCompra();
    console.log("compra milhas variavel extrato:  ", extrato);
    if (extrato && extrato.clienteId) {
      this.milhas = { id: extrato.clienteId, saldo: extrato.saldo };
      console.log("o !!!!!!!:  " + this.milhas.id + "  " + this.milhas.saldo)
    }
    this.milhasService.inserirCompraMilhas(extrato).subscribe({
      next: (response) => {
        console.log('Compra registrada com sucesso:', response);
        if (this.cliente) {
          //let milhas2 :Milhas = {id: response.clienteId, saldo: response.operacao + this.milhasSaldo}
          console.log("milhas atualizarValosrSaldo !!!!!!!:  " + this.milhas.id + "  " + this.milhas.saldo)

          //this.atualizarValorSaldo(response.clienteId, response.operacao);
          //this.atualizarValorSaldo2(response.clienteId, response.operacao);
        }
        this.valorNovo = {
          clienteId: "",
          dataHora: "",
          saldo: 0,
          operacao: 0,
          id: "",
          descricao: "",
        };

        alert('Compra de milhas registrada com sucesso!');
      },
      error: (error) => {
        console.error('Erro ao registrar compra:', error);

        // Optional: Show error message
        alert('Erro ao registrar compra. Por favor, tente novamente.');
      },
      complete: () => {
        this.isLoading = false;

      }
    });
    //let milhas2: Milhas = { id: "e8ac", saldo: this.milhas.saldo };

    this.atualizarValorSaldo(this.milhas);
    console.log("o !!!!!!!:  " + this.milhas.id + "  " + this.milhas.saldo)

  }

  teste() {
    console.log(this.valorNovo);
    //alert(this.valorNovo.operacao);
  }


  atualizarValorSaldo(milhas: Milhas): void {

    console.log("milhas atualizarValosrSaldo:  " + milhas.id + "  " + milhas.saldo)
    this.milhasService.atualizarSaldoMilhas(milhas).subscribe({
      next: response => {
        console.log('Milhas atualizadas com sucesso :', response);

        alert('Compra de milhas registrada com sucesso!');

      }
    })
  }

  atualizarValorSaldo2(cId: string, operacao: number,) {
    let saldoObj: Milhas = { id: cId, saldo: operacao + this.milhasSaldo };
    console.log("saldoOBJ: " + saldoObj.id + " " + saldoObj.saldo);
    this.milhasService.atualizarSaldoMilhas(saldoObj).subscribe({
      next: (response) => { },
      error: (error) => {
        console.error('Erro ao atualizar saldo:', error);

        // Optional: Show error message
        alert('Erro ao attualizar saldo.');
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }



  //this.milhasService.getIdByClienteId(saldoObj.id));


  /*
  getId(clienteId: any): void {
    this.milhasService.getIdByClienteId(clienteId).subscribe((id: ) => {
      this.idDoBDMilhas = id;
    });
  }
  */
  calcularValorDoSaldoNoExtrato(x: number): number {
    return x + this.getDadosForm();
  }
}

