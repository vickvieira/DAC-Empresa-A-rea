import { Component, inject } from '@angular/core';
import { ReservaService } from '../../../services/reserva.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { Reserva } from '../../../models/reserva.model';
import { CommonModule } from '@angular/common';
import { Voo } from '../../../models/voo.model';
import { VooService } from '../../../services/voo.service';
import { EMPTY, Observable, switchMap } from 'rxjs';
import { HomeClienteComponent } from '../home-cliente/home-cliente.component';
import { CancelarReservaComponent } from '../cancelar-reserva/cancelar-reserva.component';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmarCheckinReservaComponent } from '../confirmar-checkin-reserva/confirmar-checkin-reserva.component';

@Component({
  selector: 'app-consultar-reserva',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultar-reserva.component.html',
  styleUrl: './consultar-reserva.component.css'
})

export class ConsultarReservaComponent {
  //reserva!: Reserva;
  reserva: Reserva | null = null;
  voo: Voo | null = null;
  //voo$: Observable<Voo> | undefined;
  codigoReserva: string = '';
  loading = false;
  error: string | null = null;
  constructor(
    private reservaService: ReservaService,
    //private router: Router,
    //private authService: AuthService,
    private vooService: VooService,
    private modalService: NgbModal,
    //private homeCliente: HomeClienteComponent
    //private confirmarCheckin: ConfirmarCheckinReservaComponent
  ) { }

  ngOnInit(): void {
    //console.log("onnit")
  }
  /*
  consultarReserva(codigoReserva: string): void {
    this.reservaService.getReservaPorCodigoReserva(codigoReserva)
      .subscribe({
        next: (reservas)
      this.reserva
    }
    

  }
*/
  getDadosForm(): string {
    return (<HTMLInputElement>document.getElementById("codigoReserva")).value;
  }

  fetchReservation(codigoReserva: string) {
    this.loading = true;
    this.error = null;
  }

  /*
  consultarReserva(): void {
    this.codigoReserva = this.getDadosForm()
    console.log(this.codigoReserva);

    this.reservaService.getReservaPorCodigoReserva(this.codigoReserva)
      .subscribe({
        next: (reservas) => {
          if (reservas.length > 0) {
            this.reserva = reservas[0]; // pega a primeira reserva que der match 
            this.loading = false;
          } else {
            this.error = 'Nenhuma reserva com esse codigo';
            this.reserva = null;
            this.loading = false;
          }
        },
        error: (err) => {
          this.error = 'Erro: ' + err.message;
          this.reserva = null;
          this.loading = false;
        }
      });

    console.log(this.reserva?.id);
    if (this.reserva) {
      this.pegaVoo(this.reserva?.vooCodigo)
    }
  }

  pegaVoo(codigo: string) { 
    this.vooService.getVooByCodigo(codigo).subscribe({
      next: (fetchedVoo) => {
        this.voo = fetchedVoo; // Store the retrieved Voo
        console.log('Voo found:', this.voo);
      },
      error: (error) => {
        console.error('Error fetching Voo:', error);
      }
    });
  }
*/
  consultarReserva(): void {
    this.codigoReserva = this.getDadosForm();
    console.log(this.codigoReserva);

    this.reservaService.getReservaPorCodigoReserva(this.codigoReserva)
      .pipe(
        switchMap((reservas) => {
          this.loading = false;

          if (reservas.length === 0) {
            this.error = 'Nenhuma reserva com esse codigo';
            this.reserva = null;
            return EMPTY;
          }

          this.reserva = reservas[0]; // pega a primeira reserva que der match

          // se o vooCodigo existe, pega o voo certo
          return this.vooService.getVooByCodigo(this.reserva.vooCodigo);
        })
      )
      .subscribe({
        next: (fetchedVoo) => {
          this.voo = fetchedVoo;
          console.log('Voo encontrado:', this.voo);
        },
        error: (err) => {
          this.error = 'Erro: ' + err.message;
          this.reserva = null;
          this.loading = false;
        }
      });
  }

  modalCancelarReserva(reserva: Reserva) {
    const modalRef = this.modalService.open(CancelarReservaComponent);
    modalRef.componentInstance.reserva = { ...reserva };
  }

  modalFazerCheckin(reserva: Reserva) {
    //this.confirmarCheckin.modalFazerCheckin(reserva);
    //this.modalService.open(NgbdModal2Content, { size: 'lg' });
    this.confirmarCheckin(reserva);
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