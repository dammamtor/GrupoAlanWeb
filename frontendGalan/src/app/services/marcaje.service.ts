import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MarkingTechnique } from '../models/MarkingTechnique';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MarcajeService {
  private apiUrl : string = "http://localhost:8081/grupo-alan/markings"
  constructor(private http: HttpClient) { }

  //
  obtenerMarkingTechniquesenBD(): Observable<MarkingTechnique[]> {
    console.log("METODO SERVICE. OBTENER TECNICAS DE MARCAJE");
    return this.http.get<MarkingTechnique[]>(`${this.apiUrl}/list-markingtechniques`);
  }
}
