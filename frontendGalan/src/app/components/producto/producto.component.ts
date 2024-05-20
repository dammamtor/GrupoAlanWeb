import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [HeaderComponent, RouterLink, CommonModule],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.css',
})
export class ProductoComponent {
  product: Product | undefined;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const ref = params['ref']; 
      this.productService.obtenerProductoPorRef(ref).subscribe({
        next: (product: Product) => {
          this.product = product;
          console.log("Producto obtenido:", this.product);
        },
        error: (error) => {
          console.error("Error al obtener el producto:", error);
        }
      });
    });
  }

}
