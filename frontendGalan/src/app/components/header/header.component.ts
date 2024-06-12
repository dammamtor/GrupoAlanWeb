import { Component, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { TipoService } from '../../services/tipo.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  tipos: string[] = [];
  error: string | null = null;
  isLoading: boolean = true; // Variable para controlar el estado de carga

  constructor(
    private ruta: Router,
    private tipoService: TipoService,
  ) { }

  ngOnInit(): void {
    this.getTipos();
  }

  getTipos(): void {
    console.log('Obteniendo lista de tipos desde el servicio...');
    this.tipoService.obtenerListaTiposEnBD().subscribe({
      next: (tipos) => {
        this.tipos = tipos.map((tipo) =>
          tipo.replace(/^"|"$/g, '')
        );
        console.log('Lista de tipos: ', this.tipos);
        this.isLoading = false; // Se apaga el spinner cuando se han cargado los tipos
      },
      error: (error) => {
        this.error =
          'Error al cargar la lista de tipos. Por favor, inténtalo de nuevo más tarde.';
        this.isLoading = false; // En caso de error, también se apaga el spinner
      },
    });
  }

  navigateToTypes(type: string) {
    this.ruta.navigate(['types', type]);
  }

  navegateLogin() {
    this.ruta.navigate(['login']);
  }
  navegatePayment() {
    this.ruta.navigate(['payments']);
  }
  navegateCart() {
    this.ruta.navigate(['carrito']);
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
    this.ruta.navigate(['']);
  }

  irALogin(): void {
    this.ruta.navigate(['login']);
  }

  irABusquedaAvanzada(): void {
    this.ruta.navigate(['busqueda-avanzada']);
  }
}
