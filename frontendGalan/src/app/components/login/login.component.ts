import { Component, inject } from '@angular/core';
import { UserServiceService } from '../../services/user-service.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { UsuarioRequest } from '../../models/UsuarioRequest';
import { CommonModule } from '@angular/common';
import { SessionInfo } from '../../models/SessionInfo';
import { tap } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HeaderComponent, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  usuarioRequest: UsuarioRequest;
  errorMessage: string | null = null;

  constructor(
    private userService: UserServiceService,
    private router: Router
  ) {
    this.usuarioRequest = {
      nombreUsuario: '',
      contrasena: ''
    };
  }

  login() {
    console.log('Usuario Request:', this.usuarioRequest);
    this.userService.authenticateUser(this.usuarioRequest)
      .subscribe({
        next: (response: any) => {
          console.log('Respuesta del servidor:', response);
          this.getSessionInfo().subscribe({
            next: (sessionInfo: SessionInfo) => {
              console.log('Información de sesión:', sessionInfo);
            },
            error: (error) => {
              console.error('Error obteniendo información de sesión:', error);
            }
          });
        },
        error: (error) => {
          console.error('Error en la solicitud:', error);
        }
      });
  }
  
  getSessionInfo() {
    return this.userService.getSessionInfo().pipe(
      tap(console.log)
    );
  }
  
  
  redirectToAdminHome(): void {
    this.router.navigate(["admin/home"]);
  }

  redirectToRegisterUser(): void {
    this.router.navigate(["register-user"]);
  }

  redirectToRegisterProfessionalUser(): void {
    this.router.navigate(["register-professional-user"]);
  }
}
