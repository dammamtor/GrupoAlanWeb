import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-sidebar',
  standalone: true,
  imports: [],
  templateUrl: './user-sidebar.component.html',
  styleUrl: './user-sidebar.component.css'
})
export class UserSidebarComponent {
  constructor(
    private ruta: Router
  ){

  }

  redirectToPanelDeControl() {
    this.ruta.navigate(["user/home"]);
  }

  redirectToNotificaciones() {
    this.ruta.navigate(["user/notificaciones"]);
  }

  redirectToHistorial() {
    this.ruta.navigate(["user/historial"]);
  }

  redirectToFavoritos() {
    this.ruta.navigate(["user/favoritos"]);
  }

  redirectToConfiguracion() {
    this.ruta.navigate(["user/configuracion"]);
  }
}
