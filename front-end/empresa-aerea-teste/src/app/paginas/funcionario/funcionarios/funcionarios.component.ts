import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';
import { Funcionario } from '../../../models/funcionario.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmacaoExclusaoComponent } from '../../../componentes/confirmacao-exclusao/confirmacao-exclusao.component';

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgxMaskDirective],
  providers: [provideNgxMask()],
  templateUrl: './funcionarios.component.html',
  styleUrls: ['./funcionarios.component.css'],
})
export class FuncionariosComponent implements OnInit {
  formFuncionario: FormGroup;
  funcionarios: Funcionario[] = [];
  mensagemErro: string | null = null;
  mensagemSucesso: string | null = null;
  editando: boolean = false;
  cpfEdicao: string | null = null;

  constructor(private fb: FormBuilder, private modalService: NgbModal) {
    this.formFuncionario = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      email: ['', [Validators.required, Validators.email]],
      telefone: ['', [Validators.required, Validators.pattern(/^\d{10,11}$/)]],
      ativo: [true],
    });
  }

  ngOnInit(): void {
    this.carregarFuncionarios();
  }

  carregarFuncionarios(): void {
    this.funcionarios = this.funcionarios.filter((func) => func.ativo);
  }

  cadastrarFuncionario(): void {
    if (this.formFuncionario.invalid) {
      this.mensagemErro = 'Por favor, preencha todos os campos corretamente.';
      this.mensagemSucesso = null;
      return;
    }

    const dadosFuncionario: Funcionario = {
      ...this.formFuncionario.getRawValue(), // Pega valores mesmo com CPF desabilitado
      ativo: true,
    };

    if (this.editando && this.cpfEdicao) {
      const index = this.funcionarios.findIndex(
        (func) => func.cpf === this.cpfEdicao
      );
      if (index !== -1) {
        this.funcionarios[index] = {
          ...dadosFuncionario,
          ativo: true,
        };
        this.mensagemSucesso = 'Funcionário atualizado com sucesso!';
      }
    } else {
      if (this.funcionarios.some((func) => func.cpf === dadosFuncionario.cpf)) {
        this.mensagemErro = 'Já existe um funcionário cadastrado com este CPF.';
        this.mensagemSucesso = null;
        return;
      }

      this.funcionarios.push(dadosFuncionario);
      this.mensagemSucesso = 'Funcionário cadastrado com sucesso!';
    }

    this.mensagemErro = null;
    this.formFuncionario.reset({ ativo: true });
    this.formFuncionario.get('cpf')?.enable();
    this.editando = false;
    this.cpfEdicao = null;
    this.carregarFuncionarios();
  }

  editarFuncionario(cpf: string): void {
    const funcionario = this.funcionarios.find((func) => func.cpf === cpf);
    if (!funcionario) {
      this.mensagemErro = 'Funcionário não encontrado.';
      this.mensagemSucesso = null;
      return;
    }

    this.formFuncionario.patchValue(funcionario);
    this.formFuncionario.get('cpf')?.disable(); // Desabilita o CPF
    this.editando = true;
    this.cpfEdicao = cpf;
  }

  abrirModalConfirmacao(cpf: string): void {
    const modalRef = this.modalService.open(ConfirmacaoExclusaoComponent);
    modalRef.componentInstance.titulo = 'Confirmação de Exclusão';
    modalRef.componentInstance.mensagem = `Tem certeza que deseja excluir o funcionário com CPF ${cpf}?`;

    modalRef.componentInstance.confirmado.subscribe(() => {
      this.excluirFuncionario(cpf);
    });
  }

  excluirFuncionario(cpf: string): void {
    const funcionario = this.funcionarios.find((func) => func.cpf === cpf);
    if (funcionario) {
      funcionario.ativo = false;
      this.mensagemSucesso = 'Funcionário excluído com sucesso!';
      this.carregarFuncionarios();
    } else {
      this.mensagemErro = 'Funcionário não encontrado.';
    }
  }
}
