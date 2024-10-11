import { Component } from '@angular/core';
import { VooService } from '../../../services/voo.service';
import { Router } from '@angular/router';
import { Voo } from '../../../models/voo.model';
import { CurrencyPipe, DatePipe, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Cliente } from '../../../models/cliente.model';

@Component({
  selector: 'app-buscar-voos',
  standalone: true,
  imports: [NgFor, NgIf, FormsModule, CurrencyPipe, DatePipe],
  templateUrl: './buscar-voos.component.html',
  styleUrl: './buscar-voos.component.css'
})
export class BuscarVoosComponent {
  origem: string = '';
  destino: string = '';
  voos: Voo[] = [];
  mensagemErro: string = '';
  voosFiltrados: Voo[] = [];



  constructor(private vooService: VooService, private router: Router) {}

  ngOnInit(): void {
    // não é para aparecer nenhum voo no carregamento? se for o caso deixar o ngOnInit vazio ou se não:
    // this.buscarVoos('', ''); -> para que mostre todos os voos ao carregar a página

    // const clienteId = 1;
    // this.clienteService.getClienteById(clienteId).subscribe(cliente => {
    //   this.cliente = cliente; // Aqui teria acesso ao cliente ?
    // });
  }

  buscarVoos(origem: string, destino: string): void {
    this.vooService.buscarVoos(origem, destino).subscribe((voos: Voo[]) => {
      const dataAtual = new Date();
      
      // filtro no front (apenas) p garantir que só os futuros sejam exibidos
      this.voosFiltrados = voos.filter(voo => {
        const dataVoo = new Date(voo.dataHora);
        const origemMatch = !origem || voo.origem === origem;
        const destinoMatch = !destino || voo.destino === destino;
        const dataMatch = dataVoo >= dataAtual;
  
        return origemMatch && destinoMatch && dataMatch;
      });
  
      if (this.voosFiltrados.length === 0) {
        this.mensagemErro = "Nenhum voo encontrado com os critérios de busca.";
      } else {
        this.mensagemErro = "";
      }
    });
  }
  
  
  selecionarVoo(codigo: string): void {
    this.router.navigate(['/efetuar-reserva', codigo]);
  }

}
