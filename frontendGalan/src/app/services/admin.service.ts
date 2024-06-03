import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8081/grupo-alan/admin'; // URL base del backend.

  constructor(private http: HttpClient) { }

  getSession(): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/session`);
  }

  registerAdmin(userRequest: any): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/register`, userRequest);
  }

  logout(): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/logout`);
  }

  manageUser(userId: number, enable: boolean): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/manage-user/${userId}?enable=${enable}`, null);
  }
}
