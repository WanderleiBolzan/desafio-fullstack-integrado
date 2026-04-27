import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BeneficioService { // Garanta que o nome da classe seja este
  private http = inject(HttpClient);
  private readonly API = 'http://localhost:8080/api/beneficios';

  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.API);
  }

  transferir(idDe: number, idPara: number, valor: number): Observable<any> {
    return this.http.post(`${this.API}/transferir`, { idDe, idPara, valor });
  }
}
