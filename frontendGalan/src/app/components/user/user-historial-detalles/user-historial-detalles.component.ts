import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { UserSidebarComponent } from '../user-sidebar/user-sidebar.component';

@Component({
  selector: 'app-user-historial-detalles',
  standalone: true,
  imports: [HeaderComponent, UserSidebarComponent],
  templateUrl: './user-historial-detalles.component.html',
  styleUrl: './user-historial-detalles.component.css'
})
export class UserHistorialDetallesComponent {

}
