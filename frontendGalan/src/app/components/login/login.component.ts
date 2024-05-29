import { Component, inject } from '@angular/core';
import { UserServiceService } from '../../services/user-service.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { UsuarioRequest } from '../../models/UsuarioRequest';
import { CommonModule } from '@angular/common';

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
    private ruta: Router
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
          console.log('Respuesta del servidor:', response.message);
          // Manejar la respuesta del servidor, por ejemplo, guardar el token de autenticación
          this.errorMessage = null; // Limpiar el mensaje de error si la autenticación es exitosa
        },
        error: (error) => {
          console.error('Error en la solicitud:', error);
          this.errorMessage = error.error.message || 'Error en la autenticación';
        }
      });
  }

  registrarUsuario(): void {
    this.ruta.navigate(["register-user"]);
  }

  registrarEmpresa(): void {
    this.ruta.navigate(["register-user"]);
  }
}
