import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { UserSidebarComponent } from '../user-sidebar/user-sidebar.component';

@Component({
  selector: 'app-user-configuracion',
  standalone: true,
  imports: [HeaderComponent, UserSidebarComponent],
  templateUrl: './user-configuracion.component.html',
  styleUrl: './user-configuracion.component.css'
})
export class UserConfiguracionComponent {

}
