import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl : string = "http://localhost:8080/grupoalan/obtener-productos"
  constructor(private http: HttpClient) { }

  obtenerProductosBD(): Observable<Product[]> {
    console.log("METODO SERVICE. OBTENER PRODUCTOS");
    return this.http.get<Product[]>(this.apiUrl);
  }
}
