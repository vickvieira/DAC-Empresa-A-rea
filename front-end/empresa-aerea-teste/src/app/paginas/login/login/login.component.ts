import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../../../services/login.service';

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

  constructor(private fb: FormBuilder, private loginService: LoginService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required ]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { email, senha } = this.loginForm.value;

      this.loginService.login(email, senha).subscribe(
        response => {
          if (response.success) {
            // Se a senha estiver correta, redireciona para outra página ou realiza uma ação
            console.log('Login bem-sucedido');
          } else {
            this.errorMessage = 'Credenciais inválidas. Tente novamente.';
          }
        },
        error => {
          this.errorMessage = 'Ocorreu um erro. Tente novamente mais tarde.';
        }
      );
    }
  }
}