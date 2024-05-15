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
    private markingTechniqueService: MarcajeService,
    private materialService: MaterialService
  ) {}

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

  //MOVERSE A PRODUCTOS
  irAProduct() {
    this.ruta.navigate(['producto']);
    // Desplazarse al principio de la página
    const element = document.getElementById('topOfPage');
    if (element) {
      element.scrollIntoView({
        behavior: 'smooth',
        block: 'start',
        inline: 'nearest',
      });
    }
  }

  products: Product[] = [];
  categories: Category[] = [];
  colors: Color[] = [];
  materials: Description[] = [];
  markingtechniques: MarkingTechnique[] = [];
  loading: boolean = true;
  error: string | null = null;

  ngOnInit(): void {
    this.getCategories();
    this.getMarkingTechniques();
    this.getColors();
    this.getMaterials();
    this.getProducts();
  }

  getProducts(): void {
    this.loading = true; // Indicador de carga iniciado
    this.productService.obtenerProductosBD().subscribe({
      next: (products) => {
        this.products = products;
        this.loading = false; // Cambiar a falso cuando la carga se completa
      },
      error: () => {
        this.error =
          'Error al cargar productos. Por favor, inténtalo de nuevo más tarde.';
        this.loading = false;
      },
    });
  }
  getCategories(): void {
    console.log('Obteniendo categorías únicas desde el servicio...');
    this.categoryService.obtenerCategoriasUnicasEnBD().subscribe({
      next: (categories) => {
        this.categories = categories.map((category) =>
          category.replace(/^"|"$/g, '')
        );
        console.log('Categorías obtenidas: ', this.categories);
      },
      error: () => {
        this.error =
          'Error al cargar categorías. Por favor, inténtalo de nuevo más tarde.';
      },
    });
  }

  getColors(): void {
    console.log('Obteniendo lista de colores desde el servicio...');

    this.colorService.obtenerColoresUnicasEnBD().subscribe({
      next: (colors) => {
        this.colors = colors.map((color) => color.replace(/^"|"$/g, ''));
        console.log('Colores obtenidos: ', this.colors);
      },
      error: () => {
        this.error =
          'Error al cargar colores. Por favor, inténtalo de nuevo más tarde.';
      },
    });
  }

  getMarkingTechniques(): void {
    console.log('Obteniendo lista de técnicas de marcaje desde el servicio...');

    this.markingTechniqueService.obtenerMarkingTechniquesenBD().subscribe({
      next: (markingtechniques) => {
        this.markingtechniques = markingtechniques.map((markingtechnique) =>
          markingtechnique.replace(/^"|"$/g, '')
        );
        console.log('Técnicas de marcaje obtenidas: ', this.markingtechniques);
      },
      error: () => {
        this.error =
          'Error al cargar técnicas de marcaje. Por favor, inténtalo de nuevo más tarde.';
      },
    });
  }

  getMaterials(): void {
    console.log('Obteniendo lista de materiales desde el servicio...');
    this.materialService.obtenerListaMaterialesEnBD().subscribe({
      next: (materials) => {
        this.materials = materials.map((material) =>
          material.replace(/^"|"$/g, '')
        );
        console.log('Lista de materiales: ', this.materials);
      },
      error: (error) => {
        this.error =
          'Error al cargar la lista de materiales. Por favor, inténtalo de nuevo más tarde.';
      },
    });
  }

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
      // console.log("Current Products:", currentProducts);
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
