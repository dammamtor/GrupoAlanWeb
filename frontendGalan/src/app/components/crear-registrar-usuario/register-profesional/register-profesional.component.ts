import { Component } from '@angular/core';
import { UsuarioProfesionalRequest } from '../../../models/UsuarioProfesionalRequest';
import { UserServiceService } from '../../../services/user-service.service';
import { Router } from '@angular/router';
import { AccountType } from '../../../models/AccountType';
import { FormsModule, NgForm } from '@angular/forms';
import { HeaderComponent } from '../../header/header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-profesional',
  standalone: true,
  imports: [FormsModule, HeaderComponent, CommonModule],
  templateUrl: './register-profesional.component.html',
  styleUrls: ['./register-profesional.component.css'],
})
export class RegisterProfesionalComponent {
  public userRegister: UsuarioProfesionalRequest;

  constructor(private userService: UserServiceService, private router: Router) {
    this.userRegister = {
      email: '',
      password: '',
      repeatPassword: '',
      username: '',
      companyName: '',
      companyAddress: '',
      companyPhoneNumber: '',
      accountType: AccountType.PROFESSIONAL,
      city: '',
      postalCode: '',
      country: '',
    };
  }

  registerUser(form: NgForm) {
    if (this.passwordsMatch()) {
      console.log('Objeto recibido: ', this.userRegister);
      this.userService.registerProfessionalUser(this.userRegister).subscribe({
        next: (response) => {
          console.log('Usuario registrado exitosamente');
          this.messageRegistration();
        },
        error: (error) => {
          console.error('Error al registrar usuario', error);
        },
      });
    } else {
      console.error('Las contraseñas no coinciden.');
    }
  }

  passwordsMatch(): boolean {
    return this.userRegister.password === this.userRegister.repeatPassword;
  }

  messageRegistration() {
    console.log('Vamonos');
    this.router.navigate(['correct-registration', this.userRegister.username], {
      queryParams: { userType: 'professional' },
    });
  }
  redirectToLogin(): void {
    this.router.navigate(['login']);
  }
}
