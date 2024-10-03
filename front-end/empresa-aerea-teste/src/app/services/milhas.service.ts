import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Milhas } from '../models/milhas.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MilhasService {
  apiUrl = 'http://localhost:3000/milhas';
  apiUrlTransacoes = 'http://localhost:3000/transacoes_milhas';


  httpOption = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }


  getMilhasByClienteId(clienteId: number): Observable<number> {
    return this.httpClient.get<Milhas[]>(`${this.apiUrl}?clienteId=${clienteId}`)
      .pipe(
        map(milhasArray => milhasArray.length > 0 ? milhasArray[0].saldo : 0)
      );
  }

  /*
  getMilhasByClienteId(clienteId: number): Observable<number> {
    //return this.httpClient.get<number>(this.apiUrl+ "/"+ clienteId);
    return this.httpClient.get<number>(this.apiUrl);
  }
  */
  
  //precisa arrumar esse aqui ainda
  getExtratobyClienteId(clienteId: number): Observable<Milhas[]> {
    return this.httpClient.get<Milhas[]>(`${this.apiUrlTransacoes}?clienteId=${clienteId}`)
    //.pipe(map(data ))

    /*
    //return this.httpClient.get<Milhas[]>(`${this.apiUrlTransacoes}?clienteId=${clienteId}`);
    console.log("valor clienteID =  " + clienteId);
    return this.httpClient.get<Milhas[]>(this.apiUrlTransacoes, this.httpOption)
    */
  }


  // mais metodos

  getTodosExtratos(): Observable<Milhas[]> {
    return this.httpClient.get<Milhas[]>(this.apiUrlTransacoes, this.httpOption)
  }


}
