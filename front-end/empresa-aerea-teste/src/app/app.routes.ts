import { Routes } from '@angular/router';
import { HomeClienteComponent } from './paginas/cliente/home-cliente/home-cliente.component';
import { AutCadComponent } from './paginas/aut-cad/aut-cad.component';
import { BuscarVoosComponent } from './paginas/cliente/buscar-voos/buscar-voos.component';
import { EfetuarReservaComponent } from './paginas/cliente/efetuar-reserva/efetuar-reserva.component';
import { LoginComponent } from './paginas/login/login/login.component';
import { authClienteGuard } from './guard/auth-cliente.guard';
import { authNaoLogadoGuard } from './guard/auth-nao-logado.guard';
import { ExtratoMilhasComponent } from './paginas/cliente/extrato-milhas/extrato-milhas.component';
import { HomeFuncComponent } from './paginas/funcionario/home-func/home-func.component';
import { ComprarMilhasComponent } from './paginas/cliente/comprar-milhas/comprar-milhas.component';
import { CancelarReservaComponent } from './paginas/cliente/cancelar-reserva/cancelar-reserva.component';
import { CadastrarVooComponent } from './paginas/funcionario/cadastrar-voo/cadastrar-voo.component';
import { FuncionariosComponent } from './paginas/funcionario/funcionarios/funcionarios.component';
import { ConsultarReservaComponent } from './paginas/cliente/consultar-reserva/consultar-reserva.component';

export const routes: Routes = [
  {
    path: 'cliente',
    component: HomeClienteComponent,
    canActivate: [authClienteGuard],
  },
  {
    path: 'cadastrar',
    component: AutCadComponent,
    canActivate: [authNaoLogadoGuard],
  },
  {
    path: 'buscar-voos',
    component: BuscarVoosComponent,
    canActivate: [authClienteGuard],
  },
  {
    path: 'efetuar-reserva/:codigo',
    component: EfetuarReservaComponent,
    canActivate: [authClienteGuard],
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [authNaoLogadoGuard],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'extrato-milhas',
    component: ExtratoMilhasComponent,
    canActivate: [authClienteGuard],
  },
  { path: 'funcionario', component: HomeFuncComponent },
  { path: 'cadastrar-voo', component: CadastrarVooComponent },
  { path: 'funcionarios', component: FuncionariosComponent },
  {
    path: 'comprar-milhas',
    component: ComprarMilhasComponent,
    canActivate: [authClienteGuard],
  },
  {
    path: 'cancelar-reserva',
    component: CancelarReservaComponent,
    canActivate: [authClienteGuard],
  },
  {
    path: 'consultar-reserva',
    component: ConsultarReservaComponent,
    canActivate: [authClienteGuard],
  }
];
