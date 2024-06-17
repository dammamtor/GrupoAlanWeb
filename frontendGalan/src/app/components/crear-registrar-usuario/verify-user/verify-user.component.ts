import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from '../../../services/user-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-verify-user',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './verify-user.component.html',
  styleUrl: './verify-user.component.css'
})
export class VerifyUserComponent {
  verifying: boolean = true;
  verificationSuccess: boolean = false;
  verificationError: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserServiceService
  ) { }

  ngOnInit(): void {
    // Obtener el token de la URL
    const token = this.route.snapshot.params['token'];

    // Llamar al método para verificar el usuario
    this.userService.verifyUser(token).subscribe(
      (response) => {
        console.log('Verificación exitosa. Redirigiendo al inicio de sesión...');
        this.verifying = false;
        this.verificationSuccess = true;
        // Redirigir al componente de inicio de sesión después de 5 segundos
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 5000);
      },
      (error) => {
        console.error('Error al verificar el usuario', error);
        this.verifying = false;
        this.verificationError = true;
        // Manejar el error, por ejemplo, mostrar un mensaje al usuario
      }
    );
  }
}
