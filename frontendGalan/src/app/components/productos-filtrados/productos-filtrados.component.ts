import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../models/Product';
import { HeaderComponent } from '../header/header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-productos-filtrados',
  standalone: true,
  imports: [HeaderComponent, CommonModule],
  templateUrl: './productos-filtrados.component.html',
  styleUrl: './productos-filtrados.component.css'
})
export class ProductosFiltradosComponent {
  productosFiltrados: Product[] = [];

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    // Obtener datos de estado enviados desde el componente BusquedaAvanzadaComponent
    const navigationState = window.history.state;
    if (navigationState && navigationState.productos) {
      this.productosFiltrados = navigationState.productos;
      console.log('Productos filtrados:', this.productosFiltrados);
    } else {
      // Manejar el caso en que no se hayan recibido productos filtrados
      console.log('No se recibieron productos filtrados.');
    }
  }

  // Agrega estas variables
  currentPage: number = 1;
  pageSize: number = 15;

  get visibleProducts(): Product[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.productosFiltrados.slice(startIndex, startIndex + this.pageSize);
  }

  get totalPages(): number {
    return Math.ceil(this.productosFiltrados.length / this.pageSize);
  }

  nextPage() {
    this.currentPage++;
    // Hacer scroll hacia arriba
    this.scrollToTop();
  }

  previousPage() {
    this.currentPage--;
    // Hacer scroll hacia arriba
    this.scrollToTop();
  }

  // Método para hacer scroll hacia arriba de la página
  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  goToRef(ref: string) {
    // Utiliza el método navigate del servicio Router
    this.router.navigate(['/busqueda-avanzada/productos-filtrados/ref', ref]);
  }
}
