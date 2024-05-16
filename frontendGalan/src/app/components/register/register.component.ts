import { Component, inject } from '@angular/core';
import { User } from '../../models/user';
import { UserServiceService } from '../../services/user-service.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  user: User = { email: '', password: '', accountType: 'particular' }; // Modelo de usuario para el formulario de registro.
  private userService = inject(UserServiceService);

  constructor() {}

  // Método para registrar un nuevo usuario.
  register() {
    this.userService.register(this.user).subscribe((response) => {
      console.log('Se ha registrado el usuario correctamente', response); // Manejo de la respuesta del backend.
    });
  }
}
