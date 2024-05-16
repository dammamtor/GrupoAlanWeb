import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { HeaderSectionComponent } from '../header-section/header-section.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent, HeaderSectionComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
