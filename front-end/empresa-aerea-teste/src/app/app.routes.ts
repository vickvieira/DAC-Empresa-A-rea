import { Routes } from '@angular/router';
import { HomeClienteComponent } from './paginas/cliente/home-cliente/home-cliente.component';
import { AutCadComponent } from './paginas/aut-cad/aut-cad.component';
import { BuscarVoosComponent } from './paginas/cliente/buscar-voos/buscar-voos.component';
import { EfetuarReservaComponent } from './paginas/cliente/efetuar-reserva/efetuar-reserva.component';
import { LoginComponent } from './paginas/login/login/login.component';
import { authClienteGuard } from './guard/auth-cliente.guard';
import { authNaoLogadoGuard } from './guard/auth-nao-logado.guard';
import { ExtratoMilhasComponent } from './paginas/cliente/extrato-milhas/extrato-milhas.component';
import { ComprarMilhasComponent } from './paginas/cliente/comprar-milhas/comprar-milhas.component';

export const routes: Routes = [
    { path: 'cliente', component: HomeClienteComponent, canActivate: [authClienteGuard] },
    { path: 'cadastrar', component: AutCadComponent, canActivate: [authNaoLogadoGuard]},
    { path: 'buscar-voos', component: BuscarVoosComponent, canActivate: [authClienteGuard] },
    { path: 'efetuar-reserva/:codigo', component: EfetuarReservaComponent, canActivate: [authClienteGuard]},
    { path: 'login', component: LoginComponent, canActivate: [authNaoLogadoGuard]},
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'extrato-milhas', component: ExtratoMilhasComponent, canActivate: [authClienteGuard]}, 
    { path: 'comprar-milhas', component: ComprarMilhasComponent,canActivate: [authClienteGuard]}


];