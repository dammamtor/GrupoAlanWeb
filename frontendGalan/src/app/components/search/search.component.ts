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

  constructor(
    private route: ActivatedRoute,
    private router: Router,
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
      });
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
}
