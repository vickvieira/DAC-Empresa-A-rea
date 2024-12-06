import { Component, inject, Injectable, Input, TemplateRef } from '@angular/core';
import { ModalDismissReasons, NgbActiveModal, NgbDatepickerModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Reserva } from '../../../models/reserva.model';
import { ReservaService } from '../../../services/reserva.service';
import { Cliente } from '../../../models/cliente.model';
import { ClienteService } from '../../../services/cliente.service';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { ComprarMilhasComponent } from '../comprar-milhas/comprar-milhas.component';
import { ExtratoMilhas } from '../../../models/extrato-milhas.model';


@Component({
  selector: 'app-cancelar-compra',
  standalone: true,
  imports: [NgbDatepickerModule,],
  templateUrl: './cancelar-reserva.component.html',
  styleUrl: './cancelar-reserva.component.css'
})
@Injectable({
  providedIn: 'root'
})

export class CancelarReservaComponent {
  @Input() reserva!: Reserva;
  reservas: Reserva[] = [];
  //cliente: Cliente | undefined;
  isLoading: boolean = false;
  cliente!: Cliente | null;
  valorNovo!: ExtratoMilhas;
  //reserva: Reserva | undefined;
  constructor(public activeModal: NgbActiveModal,
    private reservaService: ReservaService,
    private compraMilhas: ComprarMilhasComponent,
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.isLoading = false;
    console.log(this.valorNovo);
    this.cliente = this.authService.getCliente();
    if (this.cliente && this.cliente.id) {
      this.compraMilhas.getMilhasSaldo(this.cliente.id);
    }
  }

  /*
    ngOnInit(): void {
      // Verifica se o usuário está logado
      if (!this.authService.isLoggedIn()) {
        this.router.navigate(['/login']); // Redireciona para a página de login se não estiver logado
        return;
      }
      this.reserva = 
    }
  */

  getReservas(clienteId: number): void {
    this.reservaService.getReservasByClienteId(clienteId).subscribe((data: Reserva[]) => {
      this.reservas = data;
    });
  }

  cancelarReserva(reserva: Reserva): void {
    console.log("codigo cancelar reserva: " + reserva.id);
    //this.reservaService.cancelarReserva(id).subscribe(() => {
    //this.reservas = this.reservas.filter(reserva => reserva.codigo !== codigo);
    /*
    this.reservaService.cancelarReserva(reserva).subscribe({
      complete: () => { this.getReservas(1); }
    });
    */

    this.reservaService.cancelarReserva(reserva).subscribe({
      next: response => {
        console.log('REserva cancelada com sucesso ??????:', response);
      }
    });

    this.compraMilhas.comprarMilhas('cancelamento da reserva ' + reserva.codigo, reserva.milhasUtilizadas);
    this.open();
    this.activeModal.dismiss();

  };




  // aqui pra baixo é relacionado ao modal de confirmacao
  private modalService = inject(NgbModal);
  activeModal2 = inject(NgbActiveModal);

  open() {
    this.modalService.open(NgbdModal2Content, { size: 'lg' });
  }


}



//MODAL DE CONFIRMACAO
@Component({
  standalone: true,
  template: `
      <div class="modal-header">
        <h4 class="modal-title">Cancelar Reserva</h4>
      </div>
      <div class="modal-body">
        <p>Sua Reserva foi cancelada! </p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline-secondary" (click)="fecharModalEAtualizarPagina()">Close</button>
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