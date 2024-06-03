import { Component } from '@angular/core';
import { UsuarioProfesionalRequest } from '../../models/UsuarioProfesionalRequest';
import { UserServiceService } from '../../services/user-service.service';
import { Router } from '@angular/router';
import { AccountType } from '../../models/AccountType';

@Component({
  selector: 'app-register-profesional',
  standalone: true,
  imports: [],
  templateUrl: './register-profesional.component.html',
  styleUrl: './register-profesional.component.css'
})
export class RegisterProfesionalComponent {
  public userRegister: UsuarioProfesionalRequest;
  constructor(
    private userService: UserServiceService,
    private ruta: Router
  ) {
    this.userRegister = {
      email: '',
      password: '',
      username: '',
      companyName: '',
      companyAddress: '',
      companyPhoneNumber: '',
      accountType: AccountType.PROFESSIONAL,
      city: '',
      postalCode: ''
    };
  }

}
