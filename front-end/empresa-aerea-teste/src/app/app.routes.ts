import { Routes } from '@angular/router';
import { HomeClienteComponent } from './paginas/cliente/home-cliente/home-cliente.component';
import { AutCadComponent } from './paginas/aut-cad/aut-cad.component';
import { ComprarMilhasComponent } from './paginas/cliente/comprar-milhas/comprar-milhas.component';

export const routes: Routes = [
    { path: 'cliente/:id', component: HomeClienteComponent },
    { path: 'cadastrar', component: AutCadComponent},
    { path: 'cliente/:id/comprar-milhas', component: ComprarMilhasComponent}
];