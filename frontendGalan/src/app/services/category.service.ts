import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../models/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseUrl: string = "http://localhost:8081/grupoalan/";

  constructor(private http: HttpClient) { }

  obtenerCategoriasUnicasEnBD(): Observable<string[]> {
    const apiUrl = this.baseUrl + 'obtener-categorias-unicas';
    console.log("METODO SERVICE. OBTENER CATEGORIAS UNICAS");
    return this.http.get<string[]>(apiUrl);
  }
}
