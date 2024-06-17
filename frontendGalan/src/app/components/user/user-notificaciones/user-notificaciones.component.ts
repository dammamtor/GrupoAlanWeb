import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { UserSidebarComponent } from '../user-sidebar/user-sidebar.component';

@Component({
  selector: 'app-user-notificaciones',
  standalone: true,
  imports: [HeaderComponent, UserSidebarComponent],
  templateUrl: './user-notificaciones.component.html',
  styleUrl: './user-notificaciones.component.css'
})
export class UserNotificacionesComponent {

}
