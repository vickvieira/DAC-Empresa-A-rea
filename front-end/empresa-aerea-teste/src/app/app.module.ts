import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { NavbarComponent } from './componentes/navbar/navbar.component';
import { HomeClienteComponent } from './paginas/cliente/home-cliente/home-cliente.component';
import { AppRoutingModule } from './app-routing.module';
import { ClienteModule } from './modules/cliente/cliente/cliente.module';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    // outros componentes
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClienteModule,
    
    // outros módulos
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
