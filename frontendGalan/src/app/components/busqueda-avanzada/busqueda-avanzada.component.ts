import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { CategoryService } from '../../services/category.service';
import { ColorService } from '../../services/color.service';
import { TipoService } from '../../services/tipo.service';
import { Category } from '../../models/Category';
import { Color } from '../../models/Color';
import { Description } from '../../models/Description';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Product } from '../../models/Product';

@Component({
  selector: 'app-busqueda-avanzada',
  standalone: true,
  imports: [HeaderComponent, CommonModule, FormsModule],
  templateUrl: './busqueda-avanzada.component.html',
  styleUrl: './busqueda-avanzada.component.css'
})
export class BusquedaAvanzadaComponent {
  categories: string[] = [];
  colors: string[] = [];
  tipos: string[] = [];
  error: string | null = null;
  isLoading: boolean = true;

  constructor(
    private ruta: Router,
    private productService: ProductService,
    private categoryService: CategoryService,
    private colorService: ColorService,
    private tipoService: TipoService,
  ) { }

  ngOnInit(): void {
    this.getCategories();
    this.getColors();
    this.getTipos();
  }

  getCategories(): void {
    console.log('Obteniendo categorías únicas desde el servicio...');
    this.categoryService.obtenerCategoriasUnicasEnBD().subscribe({
      next: (categories) => {
        this.categories = categories;
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
        this.colors = colors;
        console.log('Colores obtenidos: ', this.colors);
      },
      error: () => {
        this.error =
          'Error al cargar colores. Por favor, inténtalo de nuevo más tarde.';
      },
    });
  }

  getTipos(): void {
    console.log('Obteniendo lista de tipos desde el servicio...');
    this.tipoService.obtenerListaTiposEnBD().subscribe({
      next: (tipos) => {
        this.tipos = tipos.map((tipo) =>
          tipo.replace(/^"|"$/g, '')
        );
        console.log('Lista de tipos: ', this.tipos);
        this.isLoading = false; // Se apaga el spinner cuando se han cargado los tipos
      },
      error: (error) => {
        this.error =
          'Error al cargar la lista de tipos. Por favor, inténtalo de nuevo más tarde.';
        this.isLoading = false; // En caso de error, también se apaga el spinner
      },
    });
  }
  
  showError: boolean = false;

  onSubmit(event: Event): void {
    event.preventDefault();
    console.log("Estas en submit...");
    const categoriaSeleccionadas = this.categories.filter(category => {
      const checkbox = document.getElementById(category) as HTMLInputElement;
      return checkbox.checked;
    });
  
    const colorSeleccionados = this.colors.filter(color => {
      const checkbox = document.getElementById(color) as HTMLInputElement;
      return checkbox.checked;
    });
  
    const tipoSeleccionados = this.tipos.filter(tipo => {
      const checkbox = document.getElementById(tipo) as HTMLInputElement;
      return checkbox.checked;
    });
  
    console.log("Categorías seleccionadas:", categoriaSeleccionadas);
    console.log("Colores seleccionados:", colorSeleccionados);
    console.log("Tipos seleccionados:", tipoSeleccionados);
  
    this.productService.obtenerProductosFiltrados(categoriaSeleccionadas, colorSeleccionados, tipoSeleccionados)
      .subscribe((response: any) => {
        console.log("Respuesta del servicio:", response);

        if (response.productos.length === 0) {
          this.showError = true; // Mostrar el mensaje de error
        } else {
          console.log("Redirigiendo...");
          this.ruta.navigate(['/busqueda-avanzada/productos-filtrados'], { state: { productos: response.productos } });
        }
      }, error => {
        console.error("Error al obtener productos filtrados:", error);
        // Manejo del error
      });
  }
  

}
