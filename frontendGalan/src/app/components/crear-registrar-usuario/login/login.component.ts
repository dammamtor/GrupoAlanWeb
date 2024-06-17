import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../../header/header.component';
import { UsuarioRequest } from '../../../models/UsuarioRequest';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';
import { tap } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HeaderComponent, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  usuarioRequest: UsuarioRequest;
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {
    this.usuarioRequest = {
      nombreUsuario: '',
      contrasena: '',
    };
  }

  login() {
    console.log('Usuario Request:', this.usuarioRequest);
    this.authService
      .login(
        'http://localhost:8081/grupo-alan/users/login',
        this.usuarioRequest.nombreUsuario,
        this.usuarioRequest.contrasena
      )
      .subscribe({
        next: (response: any) => {
          console.log('Respuesta del servidor:', response);
          this.getSessionInfo().subscribe({
            next: (sessionInfo: any) => {
              console.log('Información de sesión:', sessionInfo);
              this.router.navigate(['/']); // Redirigir a la página principal u otra según la lógica de tu aplicación
            },
            error: (error) => {
              console.error('Error obteniendo información de sesión:', error);
            },
          });
        },
        error: (error) => {
          console.error('Error en la solicitud:', error);
          this.errorMessage =
            'Error en la autenticación. Por favor, verifica tus credenciales.';
        },
      });
  }

  getSessionInfo() {
    return this.authService.currentUser.pipe(tap(console.log));
  }

  redirectToRegisterUser(): void {
    this.router.navigate(['register-user']);
  }

  redirectToRegisterProfessionalUser(): void {
    this.router.navigate(['register-professional-user']);
  }
}
