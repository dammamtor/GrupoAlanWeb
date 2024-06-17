import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SharedModule } from '../../shared.module';
import { Variants } from '../../models/Variants';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-types',
  standalone: true,
  imports: [
    HeaderComponent,
    CommonModule,
    MatSnackBarModule,
    SharedModule,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './types.component.html',
  styleUrl: './types.component.css',
})
export class TypesComponent {
  searchTerm: string = '';
  products: Product[] = [];
  selectedVariants: { [key: number]: Variants } = {};
  loading: boolean = false;
  currentPage: number = 1;
  pageSize: number = 15;

  constructor(
    private route: ActivatedRoute,
    private ruta: Router,
    private productService: ProductService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.searchTerm = params['t'];
      console.log('PARAM: ', this.searchTerm);
      this.buscarProducto(this.searchTerm);
    });
  }

  buscarProducto(searchTerm: string): void {
    this.loading = true;
    this.products = []; // Limpiar productos antes de cargar nuevos

    this.productService
      .buscarProductosPorTipo(searchTerm)
      .subscribe((products) => {
        this.products = products;
        this.loading = false;
        console.log('PRODUCTOS DEVUELTOS: ', products);

        // Inicializar selectedVariants con la primera variante de cada producto
        for (const product of this.products) {
          if (product.variants && product.variants.length > 0) {
            this.selectedVariants[product.productId] = product.variants[0];
          }
        }
      });
  }

  addToCart(product: Product, variant: Variants) {
    if (variant) {
      this.cartService.addToCart(product, variant);
      console.log('Producto añadido al carrito:', product);
    } else {
      console.log('No se ha seleccionado ninguna variante.');
    }
  }

  get visibleProducts(): Product[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.products.slice(startIndex, startIndex + this.pageSize);
  }

  get totalPages(): number {
    return Math.ceil(this.products.length / this.pageSize);
  }

  nextPage() {
    this.currentPage++;
    this.scrollToTop();
  }

  previousPage() {
    this.currentPage--;
    this.scrollToTop();
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  irAProduct(t: string, ref: string) {
    this.ruta.navigate(['types', t, 'producto', 'ref', ref]);
  }
}


// searchTerm: string = '';
// products: Product[] = [];
// product: Product | undefined;
// loading: boolean = false; // Agrega esta variable

// constructor(
//   private route: ActivatedRoute,
//   private ruta: Router,
//   private productService: ProductService,
//   private cartService: CartService
// ) {}

// ngOnInit(): void {
//   this.route.params.subscribe((params) => {
//     this.searchTerm = params['t'];
//     console.log('PARAM: ', this.searchTerm);
//     this.buscarProducto(this.searchTerm);
//   });
// }

// buscarProducto(searchTerm: string): void {
//   this.loading = true; // Se activa cuando se inicia la búsqueda
//   this.productService
//     .buscarProductosPorTipo(searchTerm)
//     .subscribe((products) => {
//       this.products = products;
//       this.loading = false; // Se desactiva cuando se completa la búsqueda
//       console.log('PRODUCTOS DEVUELTOS: ', products);
//     });
// }

// // Añadir al carrito
// addToCart(product: Product) {
//   this.cartService.addToCart(product);
//   console.log('Producto añadido al carrito:', product);
// }

// // Agrega estas variables
// currentPage: number = 1;
// pageSize: number = 15;

// get visibleProducts(): Product[] {
//   const startIndex = (this.currentPage - 1) * this.pageSize;
//   return this.products.slice(startIndex, startIndex + this.pageSize);
// }

// get totalPages(): number {
//   return Math.ceil(this.products.length / this.pageSize);
// }

// nextPage() {
//   this.currentPage++;
//   // Hacer scroll hacia arriba
//   this.scrollToTop();
// }

// previousPage() {
//   this.currentPage--;
//   // Hacer scroll hacia arriba
//   this.scrollToTop();
// }

// // Método para hacer scroll hacia arriba de la página
// scrollToTop() {
//   window.scrollTo({ top: 0, behavior: 'smooth' });
// }

// irAProduct(t: string, ref: string) {
//   this.ruta.navigate(['types', t, 'producto', 'ref', ref]);
// }
