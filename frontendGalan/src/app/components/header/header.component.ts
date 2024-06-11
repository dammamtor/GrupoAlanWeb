import { Component, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthGoogleService } from '../../services/auth-google.service';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, FormsModule, ReactiveFormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  private ruta = inject(Router);

  navegateLogin() {
    this.ruta.navigate(['login']);
  }
  navegatePayment() {
    this.ruta.navigate(['payments']);
  }
  navegateAbanicos() {
    this.ruta.navigate(['types', 'abanico']);
  }

  navegateBotellas() {
    this.ruta.navigate(['types', 'botella']);
  }

  navegateBoligrafos() {
    this.ruta.navigate(['types', 'boligrafo']);
  }

  navegateBolsas() {
    this.ruta.navigate(['types', 'bolsa']);
  }

  navegateCamisetas() {
    this.ruta.navigate(['types', 'camiseta']);
  }

  navegateGorras() {
    this.ruta.navigate(['types', 'gorra']);
  }

  navegateLibretas() {
    this.ruta.navigate(['types', 'libreta']);
  }

  navegateMochilas() {
    this.ruta.navigate(['types', 'mochila']);
  }

  navegatePolos() {
    this.ruta.navigate(['types', 'polo']);
  }

  //ELEMENTOS DE TRABAJO
  irASearch(searchTerm: string): void {
    this.ruta.navigate(['search', searchTerm]);
  }

  irAHome(): void {
    this.ruta.navigate([""]);
  }

  irALogin(): void {
    this.ruta.navigate(["login"]);
  }

  irABusquedaAvanzada(): void {
    this.ruta.navigate(["busqueda-avanzada"]);
  }
}
