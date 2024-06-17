import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { UserSidebarComponent } from '../user-sidebar/user-sidebar.component';

@Component({
  selector: 'app-user-favoritos',
  standalone: true,
  imports: [HeaderComponent, UserSidebarComponent],
  templateUrl: './user-favoritos.component.html',
  styleUrl: './user-favoritos.component.css'
})
export class UserFavoritosComponent {

}
