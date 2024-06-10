import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsuarioRequest } from '../models/UsuarioRequest';
import { User } from '../models/user';
import { UsuarioParticularRequest } from '../models/UsuarioParticularRequest';
import { SessionInfo } from '../models/SessionInfo';
import { UsuarioProfesionalRequest } from '../models/UsuarioProfesionalRequest';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserServiceService {
  private apiUrl = 'http://localhost:8081/grupo-alan/users';

  constructor(private http: HttpClient, private authService: AuthService) {}

  login(username: string, password: string): Observable<any> {
    return this.authService.login(`${this.apiUrl}/login`, username, password);
  }

  registerUser(userDTO: UsuarioParticularRequest): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/register`, userDTO);
  }

  registerProfessionalUser(
    professionalUserDTO: UsuarioProfesionalRequest
  ): Observable<string> {
    return this.http.post<string>(
      `${this.apiUrl}/registerProfessionalUser`,
      professionalUserDTO
    );
  }

  verifyUser(token: string): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/verify?token=${token}`);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/all`);
  }

  verifyCredentials(userId: number, password: string): Observable<boolean> {
    return this.http.post<boolean>(
      `${this.apiUrl}/verify-credentials/${userId}`,
      password
    );
  }

  getSessionInfo(): Observable<SessionInfo> {
    return this.http.get<SessionInfo>(`${this.apiUrl}/session-info`);
  }

  initiatePasswordReset(email: string): Observable<string> {
    return this.http.post<string>(
      `${this.apiUrl}/reset-password?email=${email}`,
      null
    );
  }

  resetPassword(token: string, newPassword: string): Observable<string> {
    return this.http.post<string>(
      `${this.apiUrl}/reset-password/${token}`,
      newPassword
    );
  }

  deleteUser(userId: number): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/delete/${userId}`, null);
  }

  findByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/find-by-username/${username}`);
  }

  updatePassword(userId: number, newPassword: string): Observable<string> {
    return this.http.post<string>(
      `${this.apiUrl}/update-password/${userId}`,
      newPassword
    );
  }

  updateUser(userId: number, updatedUser: User): Observable<User> {
    return this.http.post<User>(
      `${this.apiUrl}/update-user/${userId}`,
      updatedUser
    );
  }
}
