import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { UserSidebarComponent } from '../user-sidebar/user-sidebar.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-user',
  standalone: true,
  imports: [HeaderComponent, UserSidebarComponent],
  templateUrl: './home-user.component.html',
  styleUrl: './home-user.component.css'
})
export class HomeUserComponent {
  constructor(
    private ruta: Router
  ){

  }

  redirectToEditUser() {
    this.ruta.navigate(["user/edit-user"]);
  }

}
