import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { NavbarComponent } from '../../componentes/navbar/navbar.component';
import { MilhasService } from '../../services/milhas.service';



@NgModule({
  declarations: [
    // NavbarComponent,
  ],
  imports: [
    CommonModule,
  ],
  providers: [
    MilhasService,
    
  ]
})
export class CompartilhadoModule { }
