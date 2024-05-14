import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Description } from '../models/Description';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MaterialService {
  private apiUrl: string = "http://localhost:8080/grupo-alan/descriptions"
  constructor(private http: HttpClient) { }

  obtenerListaMaterialesEnBD(): Observable<Description[]> {
    console.log("METODO SERVICE. OBTENER LISTA DE MATERIALES");
    return this.http.get<Description[]>(`${this.apiUrl}/lista-materiales`);
  }
}
