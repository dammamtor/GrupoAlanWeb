import { Component, QueryList, ViewChildren } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { Router, RouterLink } from '@angular/router';
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
  constructor(
    private ruta: Router,
    private productService: ProductService,
    private categoryService: CategoryService,
    private colorService: ColorService,
    private tipoService: TipoService,
  ) { }

  //MOVERSE A PRODUCTOS
  irAProduct(ref: string) {
    this.ruta.navigate(['lista-productos/producto/ref', ref]);
  }

  products: Product[] = [];
  categories: Category[] = [];
  colors: Color[] = [];
  tipos: Description[] = [];
  loading: boolean = true;
  error: string | null = null;

  ngOnInit(): void {
    this.getProducts();
  }



  getProducts(): void {
    this.loading = true; // Indicador de carga iniciado
    this.productService.obtenerProductosBD().subscribe({
      next: (products) => {
        this.products = products;
        console.log("LISTA DE PRODUCTOS: ", this.products);
        this.loading = false; // Cambiar a falso cuando la carga se completa
      },
      error: () => {
        this.error ='Error al cargar productos. Por favor, inténtalo de nuevo más tarde.';
        console.log(this.error);
        this.loading = false;
      },
    });
  }
  // getCategories(): void {
  //   console.log('Obteniendo categorías únicas desde el servicio...');
  //   this.categoryService.obtenerCategoriasUnicasEnBD().subscribe({
  //     next: (categories) => {
  //       this.categories = categories.map((category) =>
  //         category.replace(/^"|"$/g, '')
  //       );
  //       console.log('Categorías obtenidas: ', this.categories);
  //     },
  //     error: () => {
  //       this.error =
  //         'Error al cargar categorías. Por favor, inténtalo de nuevo más tarde.';
  //     },
  //   });
  // }

  // getColors(): void {
  //   console.log('Obteniendo lista de colores desde el servicio...');

  //   this.colorService.obtenerColoresUnicasEnBD().subscribe({
  //     next: (colors) => {
  //       this.colors = colors.map((color) => color.replace(/^"|"$/g, ''));
  //       console.log('Colores obtenidos: ', this.colors);
  //     },
  //     error: () => {
  //       this.error =
  //         'Error al cargar colores. Por favor, inténtalo de nuevo más tarde.';
  //     },
  //   });
  // }

  // getTipos(): void {
  //   console.log('Obteniendo lista de tipos desde el servicio...');
  //   this.tipoService.obtenerListaTiposEnBD().subscribe({
  //     next: (tipos) => {
  //       this.tipos = tipos
  //         ;
  //       console.log('Lista de tipos: ', this.tipos);
  //     },
  //     error: (error) => {
  //       this.error =
  //         'Error al cargar la lista de tipos. Por favor, inténtalo de nuevo más tarde.';
  //     },
  //   });
  // }

  currentPage: number = 1;
  pageSize: number = 15;

  // Método para calcular el índice inicial del primer producto de la página actual
  get startIndex(): number {
    return (this.currentPage - 1) * this.pageSize;
  }

  // Método para obtener los productos de la página actual
  get currentProducts(): any[] {
    // Verificar si la carga ha finalizado
    if (!this.loading) {
      const currentProducts = this.products.slice(
        this.startIndex,
        this.startIndex + this.pageSize
      );
      return currentProducts;
    } else {
      return []; // o return null;
    }
  }

  // Método para cambiar a la página siguiente
  nextPage() {
    if (this.hasNextPage()) {
      this.currentPage++;

      // Hacer scroll hacia arriba
      this.scrollToTop();
    }
  }

  prevPage() {
    if (this.hasPrevPage()) {
      this.currentPage--;

      // Hacer scroll hacia arriba
      this.scrollToTop();
    }
  }

  // Método para hacer scroll hacia arriba de la página
  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
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
