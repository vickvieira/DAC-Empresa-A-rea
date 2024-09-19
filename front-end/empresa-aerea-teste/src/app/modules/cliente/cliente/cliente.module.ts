import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeClienteComponent } from '../../../paginas/cliente/home-cliente/home-cliente.component';
import { ClienteService } from '../../../services/cliente.service';
import { MilhasService } from '../../../services/milhas.service';
import { ReservaService } from '../../../services/reserva.service';
import { ExtratoMilhasComponent } from '../../../paginas/cliente/extrato-milhas/extrato-milhas.component';


@NgModule({
  declarations: [
    HomeClienteComponent,
    ExtratoMilhasComponent,
  ],
  imports: [
    CommonModule,
  ],
  providers: [
    ClienteService,
    MilhasService,
    ReservaService
  ]
})
export class ClienteModule { }
