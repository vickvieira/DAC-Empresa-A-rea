import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Milhas } from '../models/milhas.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MilhasService {
  private apiUrl = 'http://localhost:3000/milhas';

  constructor(private http: HttpClient) {}

  getMilhasByClienteId(clienteId: number): Observable<number> {
    return this.http.get<Milhas[]>(`${this.apiUrl}?clienteId=${clienteId}`)
      .pipe(
        map(milhasArray => milhasArray.length > 0 ? milhasArray[0].saldo : 0)
      );
  }

  // mais metodos
}
