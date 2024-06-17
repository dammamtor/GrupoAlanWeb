import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { AdminSidebarComponent } from '../admin-sidebar/admin-sidebar.component';

@Component({
  selector: 'app-admin-usuarios',
  standalone: true,
  imports: [HeaderComponent, AdminSidebarComponent],
  templateUrl: './admin-usuarios.component.html',
  styleUrl: './admin-usuarios.component.css'
})
export class AdminUsuariosComponent {

}
