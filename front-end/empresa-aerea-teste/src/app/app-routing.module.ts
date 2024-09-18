import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeClienteComponent } from './paginas/cliente/home-cliente/home-cliente.component';

// const routes: Routes = [
//   { path: '', component: HomeClienteComponent },
//   // mais rotas
// ];
const routes: Routes = [
  // Passando o clienteId como parte da rota
  { path: 'cliente/:id', component: HomeClienteComponent },
  
  // rota inicial ou outras rotas
  { path: '', redirectTo: '/cliente/1', pathMatch: 'full' }, // Exemplo de redirecionamento para o clienteId 1
  // mais rotas
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
