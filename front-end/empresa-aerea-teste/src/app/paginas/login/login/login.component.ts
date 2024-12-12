import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../../../services/login.service';
import { AuthService } from '../../../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})

export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private loginService: LoginService, private authService: AuthService, private toastr: ToastrService, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { email, senha } = this.loginForm.value;

      this.loginService.login(email, senha).subscribe({
        next: response => {
          console.log('Resposta do backend:', response);

          if (response && response.email) {
            console.log('Login bem-sucedido:', response);

            localStorage.setItem('user', JSON.stringify(response));
            console.log('Dados armazenados no LocalStorage:', localStorage.getItem('user'));

            //this.router.navigate(['/cliente']);
            this.router.navigate(['/cliente']).then((navigated) => {
              if (navigated) {
                console.log('Navegação para /cliente bem-sucedida');
              } else {
                console.log('Falha na navegação para /cliente');
              }
            });


          } else {
            console.log('Resposta inválida:', response);
            this.errorMessage = 'Credenciais inválidas. Tente novamente.';
          }
        },
        error: (error) => {
          console.error('Erro na requisição:', error);
          this.errorMessage = 'Ocorreu um erro ao conectar com o servidor. Tente novamente mais tarde.';
        }
      });
    } else {
      this.errorMessage = 'Preencha todos os campos corretamente.';
    }
  }





  navigateToSignup() {
    this.router.navigate(['/cadastrar']);  // Ajuste a rota conforme o seu sistema
  }
}