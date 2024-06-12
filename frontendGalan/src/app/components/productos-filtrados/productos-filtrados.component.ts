import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../models/Product';
import { HeaderComponent } from '../header/header.component';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SharedModule } from '../../shared.module';
import { Variants } from '../../models/Variants';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-productos-filtrados',
  standalone: true,
  imports: [
    HeaderComponent,
    CommonModule,
    MatSnackBarModule,
    SharedModule,
    FormsModule,
    CommonModule,
  ],
  templateUrl: './productos-filtrados.component.html',
  styleUrl: './productos-filtrados.component.css',
})
export class ProductosFiltradosComponent {
  productosFiltrados: Product[] = [];
  selectedVariants: { [key: number]: Variants } = {}; // Añade esta línea

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    // Obtener datos de estado enviados desde el componente BusquedaAvanzadaComponent
    const navigationState = window.history.state;
    if (navigationState && navigationState.productos) {
      this.productosFiltrados = navigationState.productos;
      console.log('Productos filtrados:', this.productosFiltrados);

      // Inicializa selectedVariants con la primera variante de cada producto
      for (const product of this.productosFiltrados) {
        if (product.variants && product.variants.length > 0) {
          this.selectedVariants[product.productId] = product.variants[0];
        }
      }
    } else {
      // Manejar el caso en que no se hayan recibido productos filtrados
      console.log('No se recibieron productos filtrados.');
    }
  }

  // Añadir al carrito
  addToCart(product: Product, variant: Variants) {
    if (variant) {
      this.cartService.addToCart(product, variant);
      console.log('Producto añadido al carrito:', product);
    } else {
      console.log('No se ha seleccionado ninguna variante.');
    }
  }

  // Agrega estas variables
  currentPage: number = 1;
  pageSize: number = 15;

  get visibleProducts(): Product[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.productosFiltrados.slice(
      startIndex,
      startIndex + this.pageSize
    );
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
// export class ProductosFiltradosComponent {
//   productosFiltrados: Product[] = [];

//   constructor(
//     private route: ActivatedRoute,
//     private router: Router,
//     private cartService: CartService
//   ) {}

//   ngOnInit(): void {
//     // Obtener datos de estado enviados desde el componente BusquedaAvanzadaComponent
//     const navigationState = window.history.state;
//     if (navigationState && navigationState.productos) {
//       this.productosFiltrados = navigationState.productos;
//       console.log('Productos filtrados:', this.productosFiltrados);
//     } else {
//       // Manejar el caso en que no se hayan recibido productos filtrados
//       console.log('No se recibieron productos filtrados.');
//     }
//   }

//   // Agrega estas variables
//   currentPage: number = 1;
//   pageSize: number = 15;

//   get visibleProducts(): Product[] {
//     const startIndex = (this.currentPage - 1) * this.pageSize;
//     return this.productosFiltrados.slice(
//       startIndex,
//       startIndex + this.pageSize
//     );
//   }

//   get totalPages(): number {
//     return Math.ceil(this.productosFiltrados.length / this.pageSize);
//   }

//   // Añadir al carrito
//   addToCart(product: Product, variant: Variants) {
//     this.cartService.addToCart(product, variant);
//     console.log('Producto añadido al carrito:', product);
//   }

//   nextPage() {
//     this.currentPage++;
//     // Hacer scroll hacia arriba
//     this.scrollToTop();
//   }

//   previousPage() {
//     this.currentPage--;
//     // Hacer scroll hacia arriba
//     this.scrollToTop();
//   }

//   // Método para hacer scroll hacia arriba de la página
//   scrollToTop() {
//     window.scrollTo({ top: 0, behavior: 'smooth' });
//   }

//   goToRef(ref: string) {
//     // Utiliza el método navigate del servicio Router
//     this.router.navigate(['/busqueda-avanzada/productos-filtrados/ref', ref]);
//   }
// }
