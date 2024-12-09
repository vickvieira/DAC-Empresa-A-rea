import { Component } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { VooService } from '../../../services/voo.service';
import { AeroportoService } from '../../../services/aeroporto.service';
import { EstadoVoo } from '../../../models/voo.model';
import { CommonModule } from '@angular/common';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

@Component({
  selector: 'app-cadastrar-voo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgxMaskDirective],
  providers: [provideNgxMask()],
  templateUrl: './cadastrar-voo.component.html',
  styleUrl: './cadastrar-voo.component.css',
})
export class CadastrarVooComponent {
  formCadastroVoo: FormGroup;
  mensagemErro: string | null = null;
  mensagemSucesso: string | null = null;
  valorMilhas: number | null = null;

  constructor(
    private vooService: VooService,
    private aeroportoService: AeroportoService,
    private router: Router
  ) {
    this.formCadastroVoo = new FormGroup({
      codigo: new FormControl('', [
        Validators.required,
        Validators.pattern(/^[A-Z]{4}\d{4}$/),
      ]),
      dataHora: new FormControl('', [
        Validators.required,
        this.validarFormatoDataHora,
      ]),
      origem: new FormControl('', [Validators.required]),
      destino: new FormControl('', [Validators.required]),
      valorPassagem: new FormControl('', [
        Validators.required,
        Validators.min(0.01),
      ]),
      quantidadePoltronas: new FormControl('', [
        Validators.required,
        Validators.min(1),
      ]),
    });
  }
  converterParaMaiusculas(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.value = input.value.toUpperCase();
    this.formCadastroVoo
      .get('codigo')
      ?.setValue(input.value, { emitEvent: false });
  }

  validarFormatoDataHora(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const regexDataHora = /^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}$/;
      const valor = control.value;
      if (valor && !regexDataHora.test(valor)) {
        return { formatoInvalido: true };
      }
      return null;
    };
  }
  onValorPassagemChange(): void {
    const valor = this.formCadastroVoo.get('valorPassagem')?.value;
    if (valor && !isNaN(valor)) {
      this.valorMilhas = valor * 5;
    } else {
      this.valorMilhas = null;
    }
  }

  validarAeroportos(origem: string, destino: string): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.aeroportoService.getAeroportoByCodigo(origem).subscribe({
        next: (aeroportoOrigem) => {
          if (!aeroportoOrigem) {
            this.mensagemErro = `O aeroporto de origem (${origem}) não existe.`;
            resolve(false);
          } else {
            this.aeroportoService.getAeroportoByCodigo(destino).subscribe({
              next: (aeroportoDestino) => {
                if (!aeroportoDestino) {
                  this.mensagemErro = `O aeroporto de destino (${destino}) não existe.`;
                  resolve(false);
                } else {
                  resolve(true);
                }
              },
              error: (err) => {
                console.error(
                  '[ERROR] Falha ao validar aeroporto de destino:',
                  err
                );
                reject(err);
              },
            });
          }
        },
        error: (err) => {
          console.error('[ERROR] Falha ao validar aeroporto de origem:', err);
          reject(err);
        },
      });
    });
  }

  async cadastrarVoo(): Promise<void> {
    if (this.formCadastroVoo.invalid) {
      this.mensagemErro = 'Por favor, preencha todos os campos corretamente.';
      this.mensagemSucesso = null;
      return;
    }

    const formData = this.formCadastroVoo.value;

    const [dia, mes, ano] = formData.dataHora.split(' ')[0].split('/');
    const [hora, minuto] = formData.dataHora.split(' ')[1].split(':');
    const dataHoraISO = new Date(
      +ano,
      +mes - 1,
      +dia,
      +hora,
      +minuto
    ).toISOString();

    const valido = await this.validarAeroportos(
      formData.origem,
      formData.destino
    );

    if (!valido) return;

    const novoVoo = {
      codigo: formData.codigo,
      dataHora: dataHoraISO,
      origem: formData.origem,
      destino: formData.destino,
      valorPassagem: formData.valorPassagem,
      quantidadePoltronas: formData.quantidadePoltronas,
      poltronasOcupadas: 0,
      status: 'CONFIRMADO' as EstadoVoo, // Define explicitamente o tipo do status
    };

    this.vooService.cadastrarVoo(novoVoo).subscribe({
      next: () => {
        this.mensagemSucesso = 'Voo cadastrado com sucesso!';
        this.mensagemErro = null;
        this.formCadastroVoo.reset();
        this.valorMilhas = null;
      },
      error: (err) => {
        console.error('[ERROR] Falha ao cadastrar voo:', err);
        this.mensagemErro = 'Falha ao cadastrar voo. Tente novamente.';
        this.mensagemSucesso = null;
      },
    });
  }
}
