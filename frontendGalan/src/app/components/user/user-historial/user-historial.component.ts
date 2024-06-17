import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { UserSidebarComponent } from '../user-sidebar/user-sidebar.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-historial',
  standalone: true,
  imports: [HeaderComponent, UserSidebarComponent],
  templateUrl: './user-historial.component.html',
  styleUrl: './user-historial.component.css'
})
export class UserHistorialComponent {
  constructor(
    private ruta: Router
  ){

  }

  redirectToDetalles() {
    this.ruta.navigate(["user/historial/pedido/id"]);
  }
}
