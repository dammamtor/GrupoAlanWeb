import { Component, inject } from '@angular/core';
import { UserServiceService } from '../../services/user-service.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { UsuarioRequest } from '../../models/UsuarioRequest';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  usuarioRequest: UsuarioRequest;

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
        },
        error: (error) => {
          console.error('Error en la solicitud:', error);
          // Manejar el error, por ejemplo, mostrar un mensaje de error al usuario
        }
      });
  }  
  registrarUsuario() :void{
    this.ruta.navigate(["register-user"]);
  }
  registrarEmpresa() :void{
    this.ruta.navigate(["register-user"]);
  }
}
