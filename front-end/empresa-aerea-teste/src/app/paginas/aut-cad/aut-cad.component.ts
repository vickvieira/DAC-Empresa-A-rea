import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/User/user.service';

@Component({
  selector: 'app-aut-cad',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './aut-cad.component.html',
  styleUrls: ['./aut-cad.component.css']
})
export class AutCadComponent implements OnInit {
  formGroup: FormGroup;

  constructor(private formBuilder: FormBuilder,private userService: UserService) {
    this.formGroup = this.formBuilder.group({
      cpf: ['', [Validators.required, Validators.pattern('^[0-9]{11}$')]], // CPF com 11 dígitos
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      ruaNumero: ['', Validators.required],
      complemento: [''],
      cep: ['', [Validators.required, Validators.pattern('^[0-9]{5}-[0-9]{3}$')]], // CEP no formato 00000-000
      cidade: ['', Validators.required],
      estado: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Aqui você pode manter a inicialização, caso precise modificar o grupo de controles
  }

  onSubmit(): void {
    if (this.formGroup.valid) {
      this.userService.adicionarUsuario(this.usuario).subscribe(response => {
        console.log('Usuário adicionado:', response);
        // Limpar o formulário após o envio
        this.usuario = { nome: '', email: '' };
      });
    } else {
      console.log('Formulário inválido');
    }
  }
}