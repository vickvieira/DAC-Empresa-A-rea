import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeClienteComponent } from './paginas/cliente/home-cliente/home-cliente.component';

const routes: Routes = [
  { path: '', component: HomeClienteComponent },
  // mais rotas
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
