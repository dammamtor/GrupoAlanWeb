import { Component, QueryList, ViewChildren } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Product } from '../../models/Product';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { Image } from '../../models/Image';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/Category';
import { ColorService } from '../../services/color.service';
import { Color } from '../../models/Color';
import { MarkingTechnique } from '../../models/MarkingTechnique';
import { MarcajeService } from '../../services/marcaje.service';
import { Description } from '../../models/Description';
import { MaterialService } from '../../services/material.service';
import { TipoService } from '../../services/tipo.service';

@Component({
  selector: 'app-botellas',
  standalone: true,
  imports: [HeaderComponent, RouterLink, CommonModule],
  templateUrl: './botellas.component.html',
  styleUrl: './botellas.component.css',
})
export class BotellasComponent {
  products: Product[] = [];
  loading: boolean = true;
  error: string | null = null;
  currentPage: number = 0; // Página actual
  pageSize: number = 15; // Tamaño de la página

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private productService: ProductService,
  ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      if (params['page']) {
        this.currentPage = +params['page']; // Convierte el parámetro en número
        console.log("Estas en la pagina ", this.currentPage);
      }
      this.obtenerProductosPaginados(this.currentPage, this.pageSize);
    });
  }

  irAProduct(ref: string): void {
    this.router.navigate(['lista-productos/producto/ref', ref]);
  }

  obtenerProductosPaginados(page: number, size: number): void {
    this.loading = true;
    this.productService.obtenerProductosPaginados(page, size).subscribe(
      (data: Product[]) => {
        this.products = data;
        this.loading = false;
        this.error = null;
        console.log('Productos obtenidos:', this.products);
      },
      (error) => {
        this.loading = false;
        this.error = 'Error al obtener productos paginados';
        console.error('Error:', error);
      }
    );
  }
  
  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  paginaAnterior(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.router.navigate(['/lista-productos', this.currentPage]);
      this.obtenerProductosPaginados(this.currentPage, this.pageSize);
      this.scrollToTop();
    }
  }

  paginaSiguiente(): void {
    this.currentPage++;
    this.router.navigate(['/lista-productos', this.currentPage]);
    this.obtenerProductosPaginados(this.currentPage, this.pageSize);
    this.scrollToTop();
  }
}