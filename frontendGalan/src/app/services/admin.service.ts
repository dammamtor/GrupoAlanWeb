import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private apiUrl = 'http://localhost:8081/grupo-alan/admin';

  constructor(private http: HttpClient, private authService: AuthService) {}

  login(username: string, password: string): Observable<any> {
    return this.authService.login(`${this.apiUrl}/login`, username, password);
  }

  getSession(): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/session`);
  }

  registerAdmin(userRequest: any): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/register`, userRequest);
  }

  logout(): void {
    this.authService.logout();
  }

  manageUser(userId: number, enable: boolean): Observable<string> {
    return this.http.post<string>(
      `${this.apiUrl}/manage-user/${userId}?enable=${enable}`,
      null
    );
  }
}
