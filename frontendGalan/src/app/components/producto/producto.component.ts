import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MarkingsTranslations } from '../../models/MarkingTranslations';
import { CartService } from '../../services/cart.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SharedModule } from '../../shared.module';
import { Variants } from '../../models/Variants';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [
    HeaderComponent,
    RouterLink,
    CommonModule,
    FormsModule,
    MatSnackBarModule,
    SharedModule,
  ],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.css',
})
export class ProductoComponent {
  product: Product | undefined;
  selectedVariant: Variants | undefined;
  uniqueMarkingsWithTechniques: {
    image: string;
    height: number;
    width: number;
    techniques: { name: string; max_colors: string }[];
    translations: MarkingsTranslations[];
  }[] = [];

  whiteQuantity: number = 1;
  colorQuantity: number = 0;
  unitPrice: number = 1.41;
  totalPrice: number =
    this.unitPrice * (this.whiteQuantity + this.colorQuantity);
  taxRate: number = 0.21; // Tasa de IVA (21%)
  totalPriceWithVAT: number = this.totalPrice * (1 + this.taxRate);

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const ref = params['ref'];
      this.productService.obtenerProductoPorRef(ref).subscribe({
        next: (product: Product) => {
          this.product = product;
          console.log('Producto obtenido:', this.product);
          this.groupMarkingsByImage();
        },
        error: (error) => {
          console.error('Error al obtener el producto:', error);
        },
      });
    });
  }

  groupMarkingsByImage() {
    const imageTechniqueMap = new Map<
      string,
      {
        height: number;
        width: number;
        techniques: { name: string; max_colors: string }[];
        translations: MarkingsTranslations[];
      }
    >();

    if (this.product && this.product.markings) {
      this.product.markings.forEach((marking) => {
        const technique = {
          name: marking.markingTechniques.name,
          max_colors: marking.max_colors,
        };

        if (imageTechniqueMap.has(marking.area_img)) {
          const imageData = imageTechniqueMap.get(marking.area_img);
          imageData?.techniques.push(technique);
          if (marking.markingsTranslations) {
            imageData?.translations.push(marking.markingsTranslations);
          }
        } else {
          const imageData = {
            height: marking.height,
            width: marking.width,
            techniques: [technique],
            translations: marking.markingsTranslations
              ? [marking.markingsTranslations]
              : [],
          };
          imageTechniqueMap.set(marking.area_img, imageData);
        }
      });

      this.uniqueMarkingsWithTechniques = Array.from(
        imageTechniqueMap,
        ([image, data]) => ({
          image,
          height: data.height,
          width: data.width,
          techniques: data.techniques,
          translations: data.translations,
        })
      );
      console.log(
        'Resultado de la agrupación de marcaciones:',
        this.uniqueMarkingsWithTechniques
      );
    }
  }

  // Actualizar precio segun su cantidad
  updatePrice() {
    this.totalPrice =
      this.unitPrice * (this.whiteQuantity + this.colorQuantity);
    this.totalPriceWithVAT = this.totalPrice * (1 + this.taxRate);
  }

  // Añadir al carrito
  addToCart() {
    if (this.product && this.selectedVariant) {
      this.cartService.addToCart(this.product, this.selectedVariant);
      console.log('Producto añadido al carrito:', this.product);
    } else {
      console.log('No se ha seleccionado ninguna variante.');
    }
  }
}
