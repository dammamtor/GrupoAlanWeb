import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-section',
  standalone: true,
  imports: [],
  templateUrl: './header-section.component.html',
  styleUrl: './header-section.component.css'
})
export class HeaderSectionComponent {
  constructor(private ruta: Router,){

  }
  navigateProductos() {
    this.ruta.navigate(["lista-productos", 0]); // 0 para la página inicial
  }  
}
