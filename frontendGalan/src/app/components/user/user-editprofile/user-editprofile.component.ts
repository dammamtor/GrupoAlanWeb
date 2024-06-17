import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { UserSidebarComponent } from '../user-sidebar/user-sidebar.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-editprofile',
  standalone: true,
  imports: [HeaderComponent, UserSidebarComponent],
  templateUrl: './user-editprofile.component.html',
  styleUrl: './user-editprofile.component.css'
})
export class UserEditprofileComponent {
  constructor(
    private ruta: Router
  ){

  }

  redirectToUser() {
    this.ruta.navigate(["user/home"]);
  }
}
