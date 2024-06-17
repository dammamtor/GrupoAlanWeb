import { Component } from '@angular/core';
import { UserServiceService } from '../../../services/user-service.service';
import { FormsModule, NgForm } from '@angular/forms';
import { AccountType } from '../../../models/AccountType';
import { Router } from '@angular/router';
import { HeaderComponent } from '../../header/header.component';
import { UsuarioParticularRequest } from '../../../models/UsuarioParticularRequest';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, HeaderComponent, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  public userRegister: UsuarioParticularRequest;

  constructor(private userService: UserServiceService, private router: Router) {
    this.userRegister = {
      name: '',
      email: '',
      phoneNumber: '',
      country: '',
      city: '',
      address: '',
      postalCode: '',
      username: '',
      password: '',
      repeatPassword: '',
      accountType: AccountType.PARTICULAR,
    };
  }

  registerUser(form: NgForm) {
    if (this.passwordsMatch()) {
      console.log('Objeto recibido: ', this.userRegister);
      this.userService.registerUser(this.userRegister).subscribe({
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
    this.router.navigate(['correct-registration', this.userRegister.username]);
  }
  redirectToLogin(): void {
    this.router.navigate(['login']);
  }
}
