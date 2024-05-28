import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Description } from '../models/Description';

@Injectable({
  providedIn: 'root'
})
export class TipoService {
  private apiUrl: string = "http://localhost:8081/grupo-alan/descriptions"
  constructor(private http: HttpClient) { }

  obtenerListaTiposEnBD(): Observable<Description[]> {
    console.log("METODO SERVICE. OBTENER LISTA DE TIPOS");
    return this.http.get<Description[]>(`${this.apiUrl}/lista-tipos`);
  }
}
