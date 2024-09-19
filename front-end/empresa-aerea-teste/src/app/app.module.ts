import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { NavbarComponent } from './componentes/navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { ClienteModule } from './modules/cliente/cliente/cliente.module';
import { provideHttpClient } from '@angular/common/http';
import { CompartilhadoModule } from './modules/compartilhado/compartilhado.module';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    // outros componentes
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClienteModule
   
    
    // outros módulos
  ],
  providers: [[provideHttpClient()]],
  bootstrap: [AppComponent]
})
export class AppModule { }
