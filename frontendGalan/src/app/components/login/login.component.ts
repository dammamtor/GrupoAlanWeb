import { Component, inject } from '@angular/core';
import { User } from '../../models/user';
import { UserServiceService } from '../../services/user-service.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  user: User = { email: '', password: '', accountType: 'particular' }; // Modelo de usuario para el formulario de login.
  private userService = inject(UserServiceService);
  private router = inject(Router);

  constructor() {}

  // Método para iniciar sesión.
  login(): void {
    this.userService.login(this.user).subscribe({
      next: (response) => {
        console.log('El usuario ha iniciado sesión correctamente', response);
        // Redirigir al usuario a la página de inicio después del inicio de sesión exitoso.
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('Login error', error);
      },
    });
  }
}
