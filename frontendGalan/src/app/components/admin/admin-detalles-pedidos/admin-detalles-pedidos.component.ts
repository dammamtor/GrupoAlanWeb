import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { AdminSidebarComponent } from '../admin-sidebar/admin-sidebar.component';

@Component({
  selector: 'app-admin-detalles-pedidos',
  standalone: true,
  imports: [HeaderComponent, AdminSidebarComponent],
  templateUrl: './admin-detalles-pedidos.component.html',
  styleUrl: './admin-detalles-pedidos.component.css'
})
export class AdminDetallesPedidosComponent {

}
