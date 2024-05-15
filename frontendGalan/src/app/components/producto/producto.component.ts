import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [HeaderComponent, RouterLink],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.css',
})
export class ProductoComponent {
  products: Product[] = [];
  loading: boolean = true;
  error: string | null = null;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  private loadProducts(): void {
    this.productService.obtenerProductosBD().subscribe({
      next: (products) => this.onProductsLoadSuccess(products),
      error: (error) => this.onProductsLoadError(error),
    });
  }

  private onProductsLoadSuccess(products: Product[]): void {
    this.products = products;
    this.loading = false;
    console.log('Lista de productos:', this.products);
  }

  private onProductsLoadError(error: any): void {
    console.error('Error al cargar productos:', error);
    this.error =
      'Error al cargar productos. Por favor, inténtalo de nuevo más tarde.';
    this.loading = false;
  }
}
