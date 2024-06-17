import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { AdminSidebarComponent } from '../admin-sidebar/admin-sidebar.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-pedidos',
  standalone: true,
  imports: [HeaderComponent, AdminSidebarComponent, CommonModule],
  templateUrl: './admin-pedidos.component.html',
  styleUrl: './admin-pedidos.component.css'
})
export class AdminPedidosComponent {
  constructor(
    private ruta: Router
  ){

  }

  redirectToDetallesPedidos() {
    this.ruta.navigate(["admin/detalles-pedido"]);
  }
}
