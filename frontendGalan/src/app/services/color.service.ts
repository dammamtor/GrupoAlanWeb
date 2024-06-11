import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Color } from '../models/Color';

@Injectable({
  providedIn: 'root'
})
export class ColorService {
  private baseUrl: string = "http://localhost:8081/grupo-alan/colors/";

  constructor(private http: HttpClient) { }

  obtenerColoresUnicasEnBD(): Observable<string[]> {
    const apiUrl = this.baseUrl + 'list-colors';
    console.log("METODO SERVICE. OBTENER COLOROES UNICAS");
    return this.http.get<string[]>(apiUrl);
  }
}
