import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { Router, RouterLink } from '@angular/router';
import { Product } from '../../models/Product';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-botellas',
  standalone: true,
  imports: [HeaderComponent, RouterLink, CommonModule],
  templateUrl: './botellas.component.html',
  styleUrl: './botellas.component.css',
})
export class BotellasComponent {
  public botellasList: any[] = [];
  constructor(private ruta: Router, private productService: ProductService) { }
  navegateAbanicos() {
    this.ruta.navigate(['abanicos']);
  }
  navegateBotellas() {
    this.ruta.navigate(['botellas']);
  }
  navegateBoligrafos() {
    this.ruta.navigate(['boligrafos']);
  }
  navegateBolsas() {
    this.ruta.navigate(['bolsas']);
  }
  navegateCamisetas() {
    this.ruta.navigate(['camisetas']);
  }
  navegateGorras() {
    this.ruta.navigate(['gorras']);
  }
  navegateLibretas() {
    this.ruta.navigate(['libretas']);
  }
  navegateMochilas() {
    this.ruta.navigate(['mochilas']);
  }
  navegatePolos() {
    this.ruta.navigate(['polos']);
  }
  navegateRopaTrabajo() {
    this.ruta.navigate(['ropaTrabajo']);
  }

  products: Product[] = [];
  loading: boolean = true;
  error: string | null = null;


  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.obtenerProductosBD()
      .subscribe(
        products => {
          this.products = products;
          console.log("Lista de productos:", this.products); // Aquí se muestra la lista de productos
          this.loading = false;
        },
        error => {
          this.error = 'Error al cargar productos. Por favor, inténtalo de nuevo más tarde.';
          this.loading = false;
        }
      );
  }

  currentPage: number = 1;
  pageSize: number = 100;

  // Método para calcular el índice inicial del primer producto de la página actual
  get startIndex(): number {
    return (this.currentPage - 1) * this.pageSize;
  }

  // Método para obtener los productos de la página actual
  get currentProducts(): any[] {
    return this.products.slice(this.startIndex, this.startIndex + this.pageSize);
  }

  // Método para cambiar a la página siguiente
  nextPage() {
    if (this.hasNextPage()) {
      this.currentPage++;
    }
  }

  // Método para cambiar a la página anterior
  prevPage() {
    if (this.hasPrevPage()) {
      this.currentPage--;
    }
  }

  // Método para verificar si hay una página siguiente
  hasNextPage(): boolean {
    return this.startIndex + this.pageSize < this.products.length;
  }

  // Método para verificar si hay una página anterior
  hasPrevPage(): boolean {
    return this.currentPage > 1;
  }
}
