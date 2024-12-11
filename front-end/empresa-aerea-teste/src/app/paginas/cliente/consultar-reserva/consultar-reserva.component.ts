import { Component } from '@angular/core';
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
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


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
    private router: Router,
    private authService: AuthService,
    private vooService: VooService,
    private modalService: NgbModal,
    //private homeCliente: HomeClienteComponent
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
          console.log('Voo found:', this.voo);
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
    //falta fazer 
    alert("aqui irá confirmar checkin")
  }

}

