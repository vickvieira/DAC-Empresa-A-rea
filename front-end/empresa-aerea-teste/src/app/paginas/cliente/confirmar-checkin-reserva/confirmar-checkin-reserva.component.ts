import { Component, inject } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { Cliente } from '../../../models/cliente.model';
import { CommonModule } from '@angular/common';
import { Reserva } from '../../../models/reserva.model';
import { CancelarReservaComponent } from '../cancelar-reserva/cancelar-reserva.component';
import { ClienteService } from '../../../services/cliente.service';
import { ReservaService } from '../../../services/reserva.service';
import { VooService } from '../../../services/voo.service';
import { catchError, forkJoin, map, of, switchMap } from 'rxjs';
import { Voo } from '../../../models/voo.model';

@Component({
  selector: 'app-confirmar-checkin-reserva',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './confirmar-checkin-reserva.component.html',
  styleUrl: './confirmar-checkin-reserva.component.css'
})

export class ConfirmarCheckinReservaComponent {
  cliente: Cliente | undefined;
  reservasComDetalhes: any[] = [];
  clienteId: number | null = null;
  reservas: Reserva[] = [];
  voos: any[] = [];
  dataFinal: Date | undefined;


  constructor(
    private clienteService: ClienteService,
    private authService: AuthService,
    private reservaService: ReservaService,
    private vooService: VooService,
    //public activeModal: NgbActiveModal,
    private router: Router,
    private modalService: NgbModal,


  ) { }


  ngOnInit(): void {
    // Verifica se o usuário está logado
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']); // Redireciona para a página de login se não estiver logado
      return;
    }

    const dataInicial = new Date();
    this.dataFinal = new Date(dataInicial.getTime() + 172800000 * 2); // 48 horas adiante
    console.log("DEBUG: 48h daqui: ", this.dataFinal);
    //faço o get de todos os voos e depois filtro os das últimas 48hrs direto no front... não é o ideal mas tava dando muito BO tentar filtrar do json-server
    /*
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
    */

    this.clienteId = this.authService.getClienteId();
    if (this.clienteId) {
      this.getCliente(this.clienteId);
      this.fetchReservasAguardandoCheckin();
    } else {
      this.router.navigate(['/login']); // Caso o ID do cliente não seja encontrado, redireciona para login
    }

  }

  getCliente(id: number): void {
    this.clienteService.getClienteById(id).subscribe((data: Cliente) => {
      this.cliente = data;
    });
  }


  fetchReservasAguardandoCheckin(): void {
    console.log('[DEBUG] Atualizando reservas AguardandoCheckin...');
    if (!this.clienteId) {
      console.error('[ERROR] Cliente ID não encontrado.');
      return;
    }
    this.reservaService.getReservasProntasParaCheckin().subscribe({
      next: (reservas) => {
        this.reservas = reservas;
        const detalhesCombinados: any[] = [];
        reservas.forEach((reserva) => {
          this.vooService.getVooByCodigo(reserva.vooCodigo).subscribe({
            next: (voo) => {
              detalhesCombinados.push({ ...reserva, voo }); // Combina voo dinamicamente
              // Atualiza apenas após todas as reservas terem os detalhes do voo carregados       "dataHoraReserva": "2024-12-08T22:07:33.260Z",
              const dataHoraDate = new Date(voo.dataHora);
              //console.log("DEBUG: dataHoraDate dentro do subscribe:", dataHoraDate);

              if (this.dataFinal)
                if ((dataHoraDate < this.dataFinal) && (detalhesCombinados.length === reservas.length)) {
                  this.reservasComDetalhes = detalhesCombinados
                    .filter((item) => item.voo) // Exclui reservas sem detalhes de voo (se aplicável)

                    .sort(
                      (a, b) =>
                        new Date(b.voo.dataHora).getTime() -
                        new Date(a.voo.dataHora).getTime()
                    );
                  console.log(
                    '[DEBUG] Reservas com detalhes carregadas:',
                    this.reservasComDetalhes
                  );
                }
            },
            error: (err) => {
              console.error(`[ERROR] Falha ao buscar detalhes do voo ${reserva.vooCodigo}: `, err
              );
            },
          });
        });
      },
      error: (err) => {
        console.error('[ERROR] Falha ao buscar reservas:', err);
      },
    });
  }

  confirmarCheckin(reserva: Reserva): void {
    console.log('[DEBUG] Confirmar checkin:', reserva);

    this.reservaService.confirmarCheckin(reserva).subscribe({
      next: (reservaAtualizada) => {
        console.log(
          '[DEBUG] Checkin confirmado com sucesso:',
          reservaAtualizada
        );

        // Reembolsa as milhas, se aplicável


        this.open(); // Modal de confirmação
        //this.activeModal.dismiss(); // Fecha o modal atual
      },
      error: (err) => {
        console.error('[ERROR] Falha ao cancelar reserva:', err);
        alert('Erro ao cancelar a reserva. Tente novamente.');
      },
    });
  }

  modalFazerCheckin(reserva: Reserva) {
    //falta fazer 
    //alert("aqui irá confirmar checkin");
    //private modalService = inject(NgbModal);
    //activeModal2 = inject(NgbActiveModal);
    //this.reservaService.confirmarCheckin 
    this.confirmarCheckin(reserva);
    //this.open();
  }

  open() {
    this.modalService.open(NgbdModal2Content, { size: 'lg' });
  }


}

@Component({
  standalone: true,
  template: `
    <div class="modal-header">
      <h4 class="modal-title">Confirmar Check-in</h4>
    </div>
    <div class="modal-body">
      <p>Seu Check-in foi confirmado!</p>
    </div>
    <div class="modal-footer">
      <button
        type="button"
        class="btn btn-outline-secondary"
        (click)="fecharModalEAtualizarPagina()"
      >
        Close
      </button>
    </div>
  `,

})
export class NgbdModal2Content {
  activeModal = inject(NgbActiveModal);

  fecharModalEAtualizarPagina() {
    this.activeModal.close();
    //this.atualizarPagina();
  }
  atualizarPagina(): void {
    window.location.reload();
  }
}
