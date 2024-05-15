import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';

@Component({
  selector: 'app-types',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './types.component.html',
  styleUrl: './types.component.css'
})
export class TypesComponent {
  searchTerm: string = "";
  products: Product[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.searchTerm = params['t']; 
      console.log("PARAM: " , this.searchTerm);// Se cambia 's' por 'tipo' si este es el nombre correcto del parámetro
      this.buscarProducto(this.searchTerm);
    });
  }

  buscarProducto(searchTerm: string): void {
    this.productService.buscarProductosPorTipo(searchTerm)
      .subscribe(products => {
        this.products = products;
        console.log("PRODUCTOS DEVUELTOS: ", products);
      });
  }
}
