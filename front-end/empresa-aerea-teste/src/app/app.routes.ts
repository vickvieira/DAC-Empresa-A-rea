import { Routes } from '@angular/router';
import { HomeClienteComponent } from './paginas/cliente/home-cliente/home-cliente.component';
import { AutCadComponent } from './paginas/aut-cad/aut-cad.component';
import { BuscarVoosComponent } from './paginas/cliente/buscar-voos/buscar-voos.component';
import { EfetuarReservaComponent } from './paginas/cliente/efetuar-reserva/efetuar-reserva.component';

export const routes: Routes = [
    { path: 'cliente/:id', component: HomeClienteComponent },
    { path: 'cadastrar', component: AutCadComponent},
    { path: 'buscar-voos', component: BuscarVoosComponent },
    { path: 'efetuar-reserva/:codigo', component: EfetuarReservaComponent}
];