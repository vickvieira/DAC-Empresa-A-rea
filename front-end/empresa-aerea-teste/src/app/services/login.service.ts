import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,of } from 'rxjs';
import { ClienteService } from './cliente.service'; // Importar o ClienteService
import { Login } from '../models/login.models';
import { Cliente } from '../models/cliente.model';
import { Endereco } from '../models/endereco.models';
import { EnderecoService } from './endereco.service';
import { map, catchError } from 'rxjs/operators';  // Importe o map e catchError corretamente


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private apiUrl = 'http://localhost:3000/login'; // URL para o json-server
  constructor(private http: HttpClient, private clienteService: ClienteService, private enderecoService: EnderecoService) { }

  // Método para adicionar um usuário e salvar o cliente
  adicionarUsuario(formCliente: any): Observable<any> {
    // Primeiro faz o POST para adicionar o usuário
    // Inicializa a variável login corretamente
    const login: Login = {
      email: formCliente.email,
      senha: "senha"
      // Se houver mais campos, adicione aqui
    };

    const cliente: Cliente = {
      nome: formCliente.nome,
      email: formCliente.email,
      cpf: formCliente.cpf,
      telefone: formCliente.telefone,
      tipo: 'C'
    }

    const endereco: Endereco = {
      rua: formCliente.rua,
      numero: formCliente.numero,
      complemento: formCliente.complemento,
      cidade: formCliente.cidade,
      estado: formCliente.estado,
      cep: formCliente.cep
    }

    this.clienteService.addCliente(cliente).subscribe(
      response => {
        console.log('Cliente salvo com sucesso:', response);
        this.enderecoService.addEndereco(endereco).subscribe(
          response => {
            // Aqui você pode verificar a resposta do servidor
            console.log('Endereço salvo com sucesso:', response);
          },
          error => {
            // Aqui você pode capturar e debugar o erro, caso ocorra
            console.error('Erro ao salvar o endereço:', error);
          }
        );
      },
      error => {
        console.error('Erro ao salvar o cliente:', error);
      }
    );
    return this.http.post<any>(this.apiUrl, login);
  }

  login(email: string, senha: string): Observable<any> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map(users => {
        const user = users.find(u => u.email === email && u.senha === senha);
        if (user) {
          return { success: true, user };
        } else {
          return { success: false, message: 'Email ou senha incorretos' };
        }
      }),
      catchError(() => of({ success: false, message: 'Erro ao conectar com o servidor' }))
    );
  }

}