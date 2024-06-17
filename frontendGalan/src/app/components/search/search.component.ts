import { Component, HostListener } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [HeaderComponent, RouterLink, CommonModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  searchTerm: string = "";
  products: Product[] = [];
  startIndex: number = 0;
  endIndex: number = 14; // Inicialmente mostramos los primeros 15 elementos
  showScrollToTopBtn: boolean = false;
  productsWithUniqueColors: { product: Product, uniqueColors: string[] }[] = [];


  constructor(
    private route: ActivatedRoute,
    private ruta: Router,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.searchTerm = params['s'];
      this.buscarProducto(this.searchTerm);
    });
  }

  buscarProducto(searchTerm: string): void {
    this.productService.buscarProductosPorTermino(searchTerm)
      .subscribe(products => {
        this.products = products;
        this.productsWithUniqueColors = this.products.map(product => ({
          product: product,
          uniqueColors: this.extractUniqueColors(product)
        }));
      });
  }

  extractUniqueColors(product: Product): string[] {
    const uniqueColorsSet = new Set<string>();
    if (product.variants && product.variants.length > 0) {
      product.variants.forEach(variant => {
        if (variant.colorSet && variant.colorSet.name) {
          uniqueColorsSet.add(variant.colorSet.url);
        }
      });
    }
    return Array.from(uniqueColorsSet);
  }

  avanzar(): void {
    if (this.endIndex < this.products.length - 1) {
      this.startIndex += 15;
      this.endIndex += 15;
    }
  }

  retroceder(): void {
    if (this.startIndex > 0) {
      this.startIndex -= 15;
      this.endIndex -= 15;
    }
  }

  scrollToTop(): void {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  }

  @HostListener('window:scroll', [])
  onWindowScroll(): void {
    if (window.pageYOffset > 300) {
      this.showScrollToTopBtn = true;
    } else {
      this.showScrollToTopBtn = false;
    }
  }

  irAProduct(ref: string) {
    this.ruta.navigate(['search', this.searchTerm, 'producto', 'ref', ref]);
  }
}
