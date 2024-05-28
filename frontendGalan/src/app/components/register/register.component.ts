import { Component, inject } from '@angular/core';
import { UserServiceService } from '../../services/user-service.service';
import { FormsModule } from '@angular/forms';
import { AccountType } from '../../models/AccountType';
import { Router } from '@angular/router';
import { User } from '../../models/user';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  public userRegister: User;
  constructor(
    private userService: UserServiceService,
    private ruta: Router
  ) {
    this.userRegister = {
      id: -1,
      email: "",
      password: "",
      username: "",
      accountType: AccountType.PARTICULAR
    };
  }

  registerUser() {
    console.log("Objeto recibido: ", this.userRegister);
    this.userService.registerUser(this.userRegister).subscribe({
      next: (response) => {
        console.log('Usuario registrado exitosamente');
        // Aquí puedes redirigir a la página de inicio de sesión u otra página de tu aplicación
      },
      error: (error) => {
        console.error('Error al registrar usuario', error);
        // Aquí puedes manejar el error de registro, por ejemplo, mostrar un mensaje al usuario
      }
    });
  }
}
