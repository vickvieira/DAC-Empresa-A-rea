import { Component } from '@angular/core';
import { MilhasService } from '../../../services/milhas.service';
import { ClienteService } from '../../../services/cliente.service';
import { ExtratoMilhas } from '../../../models/extrato-milhas.model';
import { Router } from '@angular/router';
import { Cliente } from '../../../models/cliente.model';
import { AuthService } from '../../../services/auth.service';

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

  }

  getDadosForm(): number {
    return parseFloat((<HTMLInputElement>document.getElementById("compra")).value);
  }

  montaDadosCompra(): ExtratoMilhas {
    this.cliente = this.authService.getCliente();
    //esse if é pra nao dar problema com o undefined
    if (this.cliente && this.cliente.id) {
      this.valorNovo = {clienteId: this.cliente.id?.toString(), 
        dataHora: (new Date().toISOString()),
        operacao: this.getDadosForm(), saldo:0}
    } 
    console.log("Resultado montaDadosCompra: " + this.valorNovo)
    return this.valorNovo;
  }



  comprarMilhas(): void {
    this.isLoading = true;
    this.milhasService.inserirCompraMilhas(this.montaDadosCompra()).subscribe({
      next: (response) => {
        console.log('Compra registrada com sucesso:', response);
        this.valorNovo = {
          clienteId: "",
          dataHora: "",
          saldo: 0,
          operacao: 0,
          id: ""
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
  }
  teste() {
    console.log(this.valorNovo);
    //alert(this.valorNovo.operacao);
  }

}
