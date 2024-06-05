import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-sidebar',
  standalone: true,
  imports: [],
  templateUrl: './admin-sidebar.component.html',
  styleUrl: './admin-sidebar.component.css'
})
export class AdminSidebarComponent {
  constructor(
    private ruta: Router
  ){

  }

  redirectToPanelDeControl() {
    this.ruta.navigate(["admin/home"]);
  }

  redirectToUsuariosPanel() {
    this.ruta.navigate(["admin/users"]);
  }

  redirectToPedidosPanel() {
    this.ruta.navigate(["admin/pedidos"]);
  }
}
