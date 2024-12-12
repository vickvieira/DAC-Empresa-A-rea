import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router'; 
import { ToastrService } from 'ngx-toastr'; 

@Component({
  selector: 'app-aut-cad',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './aut-cad.component.html',
  styleUrls: ['./aut-cad.component.css']
})
export class AutCadComponent implements OnInit {
  formGroup: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private loginService: LoginService, private router: Router, private toastr: ToastrService){ // Injete o serviço de mensagens) {
    this.formGroup = this.formBuilder.group({
      cpf: ['', [Validators.required, Validators.pattern('^[0-9]{11}$')]],
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefone: ['', [Validators.required, Validators.pattern('^[0-9]{10,11}$')]],
      rua: ['', Validators.required],
      numero: ['', [Validators.required, Validators.pattern('^[0-9]+[a-zA-Z]?$')]],
      cep: ['', [Validators.required, Validators.pattern('^[0-9]{5}-[0-9]{3}$')]],
      cidade: ['', Validators.required],
      estado: ['', Validators.required],
      complemento: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }
  
//ajustado p o novo método adicionarUsuario do login.service
  onSubmit(): void {
    if (this.formGroup.valid) {
      this.loginService.adicionarUsuario(this.formGroup.value).subscribe({
        next: () => {
          this.toastr.success('Cadastro enviado para processamento.', 'Sucesso');
          this.router.navigate(['/login']);
        },
        error: (err) => {
          const errorMessage = err.error?.message || 'Erro ao enviar o cadastro.';
          this.toastr.error(errorMessage, 'Erro');
        },
      });
    } else {
      this.toastr.error('Erro ao preencher o cadastro.', 'Erro');
    }
  }
  
}