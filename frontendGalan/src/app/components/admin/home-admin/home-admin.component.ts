import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { Router } from '@angular/router';
import { AdminSidebarComponent } from '../admin-sidebar/admin-sidebar.component';

@Component({
  selector: 'app-home-admin',
  standalone: true,
  imports: [HeaderComponent, AdminSidebarComponent],
  templateUrl: './home-admin.component.html',
  styleUrl: './home-admin.component.css'
})
export class HomeAdminComponent {
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
}
