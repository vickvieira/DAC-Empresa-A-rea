import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../../../services/login.service';
import { AuthService } from '../../../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';


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
      senha: ['', [Validators.required ]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { email, senha } = this.loginForm.value;
  
      this.loginService.login(email, senha).subscribe(
        response => {
          if (response.success) {
            this.authService.login(email).subscribe(
              cliente => {
                this.router.navigate(['/cliente']);
              },
              error => {
                this.errorMessage = 'Erro ao buscar informações do cliente. Tente novamente.';
              }
            );
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

  navigateToSignup() {
    this.router.navigate(['/cadastrar']);  // Ajuste a rota conforme o seu sistema
  }
}