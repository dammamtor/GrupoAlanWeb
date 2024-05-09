import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../models/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseUrl: string = "http://localhost:8080/grupoalan/";

  constructor(private http: HttpClient) { }

  obtenerCategoriasBD(): Observable<Category[]> {
    const apiUrl = this.baseUrl + 'obtener-categorias';
    console.log("METODO SERVICE. OBTENER CATEGORIAS");
    return this.http.get<Category[]>(apiUrl);
  }
  obtenerCategoriasUnicasEnBD(): Observable<Category[]> {
    const apiUrl = this.baseUrl + 'obtener-categorias-unicas';
    console.log("METODO SERVICE. OBTENER CATEGORIAS UNICAS");
    return this.http.get<Category[]>(apiUrl);
  }
}
