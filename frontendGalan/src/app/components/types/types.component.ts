import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-types',
  standalone: true,
  imports: [HeaderComponent, CommonModule],
  templateUrl: './types.component.html',
  styleUrl: './types.component.css'
})
export class TypesComponent {
  searchTerm: string = "";
  products: Product[] = [];
  loading: boolean = false; // Agrega esta variable

  constructor(
    private route: ActivatedRoute,
    private ruta: Router,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.searchTerm = params['t'];
      console.log("PARAM: ", this.searchTerm);
      this.buscarProducto(this.searchTerm);
    });
  }

  buscarProducto(searchTerm: string): void {
    this.loading = true; // Se activa cuando se inicia la búsqueda
    this.productService.buscarProductosPorTipo(searchTerm)
      .subscribe(products => {
        this.products = products;
        this.loading = false; // Se desactiva cuando se completa la búsqueda
        console.log("PRODUCTOS DEVUELTOS: ", products);
      });
  }

  // Agrega estas variables
  currentPage: number = 1;
  pageSize: number = 15;

  get visibleProducts(): Product[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.products.slice(startIndex, startIndex + this.pageSize);
  }

  get totalPages(): number {
    return Math.ceil(this.products.length / this.pageSize);
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

  irAProduct(t: string, ref: string) {
    this.ruta.navigate(['types', t, 'producto','ref', ref]);
  }  

}
