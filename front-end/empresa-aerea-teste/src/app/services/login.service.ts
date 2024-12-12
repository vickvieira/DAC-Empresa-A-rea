import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { ClienteService } from './cliente.service'; // Importar o ClienteService
import { Login } from '../models/login.models';
import { Cliente } from '../models/cliente.model';
import { Endereco } from '../models/endereco.models';
import { EnderecoService } from './endereco.service';
import { map, catchError, tap } from 'rxjs/operators';  // Importe o map e catchError corretamente


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private apiUrl = 'http://localhost:3001'; // URL para API Gateway
  private httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json'
    })
  };
  constructor(private http: HttpClient, private clienteService: ClienteService, private enderecoService: EnderecoService) { }

  // Método para adicionar um usuário e salvar o cliente
  adicionarUsuario(formCliente: any): Observable<any> {
    // Primeiro faz o POST para adicionar o usuário
    // Inicializa a variável login corretamente
    const login: Login = {
      email: formCliente.email,
      senha: "senha"
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
    return this.http.post<any>(this.apiUrl, login);     //alterar p/ endpoint agaClienteUsuario
  }

  login(email: string, senha: string): Observable<any> {
    const login = { email, senha };

    console.log('Enviando requisição para login:', login);

    return this.http.post<any>(`${this.apiUrl}/Auth/login`, JSON.stringify(login), this.httpOptions).pipe(
      tap((response: any) => console.log('Resposta recebida do backend:', response)), // Log para depuração
      catchError(error => {
        console.error('Erro ao conectar com o servidor:', error);
        return of({ success: false, message: 'Erro ao conectar com o servidor' });
      })
    );
  }


}



// public login(auth: Auth): Observable<User> {
//   return this.http.post<any>(`${this.BASE_URL}/login`, JSON.stringify(auth), this.httpOptions)
//     .pipe(
//       map(response => {
//         const userData = response.data.user;
//         const token = response.token; // Extrai o token da resposta

//         // Armazena o token em localStorage
//         localStorage.setItem('authToken', token);

//         // Cria e retorna o objeto User
//         return new User(
//           undefined,
//           undefined,
//           userData.login,
//           undefined,
//           userData.role
//         );
//       }),
//       catchError(error => {
//         if (error.error && error.error.message) {
//           return throwError(error.error.message);
//         }
//         return throwError('Erro ao fazer login. Tente novamente mais tarde.');
//       })
//     );
// }