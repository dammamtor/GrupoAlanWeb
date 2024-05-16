import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserServiceService {
  private apiUrl = 'http://localhost:8080/api/users'; // URL base del backend.

  constructor(private http: HttpClient) {}

  // Metodo para registrar a un usuario
  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, user);
  }

  // Método para iniciar sesión.
  login(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/login`, user);
  }

  // Método para habilitar un usuario profesional.
  enableUser(userId: number): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/enable/${userId}`, {});
  }
}
