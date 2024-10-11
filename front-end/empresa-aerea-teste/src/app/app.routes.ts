import { Routes } from '@angular/router';
import { HomeClienteComponent } from './paginas/cliente/home-cliente/home-cliente.component';
import { AutCadComponent } from './paginas/aut-cad/aut-cad.component';
import { BuscarVoosComponent } from './paginas/cliente/buscar-voos/buscar-voos.component';
import { EfetuarReservaComponent } from './paginas/cliente/efetuar-reserva/efetuar-reserva.component';
import { LoginComponent } from './paginas/login/login/login.component';

export const routes: Routes = [
    { path: 'cliente', component: HomeClienteComponent },
    { path: 'cadastrar', component: AutCadComponent},
    { path: 'buscar-voos', component: BuscarVoosComponent },
    { path: 'efetuar-reserva/:codigo', component: EfetuarReservaComponent},
    { path: 'login', component: LoginComponent},
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];