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
  styleUrl: './producto.component.css'
})
export class ProductoComponent {
  products: Product[] = [];
  loading: boolean = true;
  error: string | null = null;

  constructor(private productService: ProductService) { }

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
}
