import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl : string = "http://localhost:8081/grupo-alan/productos"
  constructor(private http: HttpClient) { }

  obtenerProductosBD(): Observable<Product[]> {
    console.log("METODO SERVICE. OBTENER PRODUCTOS");
    return this.http.get<Product[]>(`${this.apiUrl}/obtener-productos`);
  }

  buscarProductosPorTermino(searchTerm: string): Observable<Product[]> {
    console.log("METODO SERVICE. BUSCAR PRODUCTOS POR TÉRMINO: " + searchTerm);
    return this.http.get<Product[]>(`${this.apiUrl}/buscar-productos/${searchTerm}`);
  }

  buscarProductosPorTipo(tipo: string): Observable<Product[]> {
    console.log("MÉTODO SERVICE. BUSCAR PRODUCTOS POR TIPO: " + tipo);
    return this.http.get<Product[]>(`${this.apiUrl}/buscar-productos-por-tipo/${tipo}`);
  }

  obtenerProductoPorRef(ref: string): Observable<Product> {
    console.log("MÉTODO SERVICE. OBTENER PRODUCTO POR REFERENCIA: " + ref);
    return this.http.get<Product>(`${this.apiUrl}/obtener-producto/${ref}`);
  }

  obtenerProductosFiltrados(categorias: string, colores: string[], tipos: string[]): Observable<Product[]> {
    const params = {
      categorias: categorias,
      colores: colores.join(","),
      tipos: tipos.join(",")
    };
    return this.http.get<Product[]>(`${this.apiUrl}/productos-filtrados`, { params: params });
  }
}
