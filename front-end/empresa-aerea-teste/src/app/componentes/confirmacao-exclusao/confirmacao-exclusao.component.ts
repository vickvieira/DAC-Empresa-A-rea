import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbActiveModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-confirmacao-exclusao',
  standalone: true,
  imports: [CommonModule, NgbModalModule],
  templateUrl: './confirmacao-exclusao.component.html',
  styleUrls: ['./confirmacao-exclusao.component.css'],
})
export class ConfirmacaoExclusaoComponent {
  @Input() titulo?: string; // Título do modal
  @Input() mensagem?: string; // Mensagem do modal
  @Output() confirmado = new EventEmitter<void>(); // Evento para confirmar exclusão

  constructor(public activeModal: NgbActiveModal) {}

  cancelar(): void {
    this.activeModal.dismiss(); // Fecha o modal
  }

  confirmar(): void {
    this.confirmado.emit(); // Emite o evento de confirmação
    this.activeModal.close();
  }
}
