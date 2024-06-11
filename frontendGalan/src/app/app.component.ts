import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/home/home.component';

const MODULES = [CommonModule, RouterOutlet, HomeComponent];

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HeaderComponent, MODULES],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'frontendGalan';
}
