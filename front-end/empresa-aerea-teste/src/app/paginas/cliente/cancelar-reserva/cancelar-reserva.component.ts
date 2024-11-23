import { Component, Inject } from '@angular/core';

import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';


@Component({
  selector: 'app-cancelar-compra',
  standalone: true,
  imports: [],
  templateUrl: './cancelar-reserva.component.html',
  styleUrl: './cancelar-reserva.component.css'
})
export class CancelarReservaComponent {
  /*
    ngOnInit(): void {
  
    }

  */
    openDialog(): void {
      const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
        width: '250px',
        data: {name: this.name, animal: this.animal}
      });
  
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        this.animal = result;
      });
    }
  
  }

}