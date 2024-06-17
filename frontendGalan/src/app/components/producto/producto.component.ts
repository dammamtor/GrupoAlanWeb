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

// Declaración de bootstrap
declare var bootstrap: any;

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
  uniqueColors: {
    name: string,
    sizes: string[],
    url: string  // Added URL property for storing color URL
  }[] = [];
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
          this.findUniqueColors();
          this.groupMarkingsByImage();
        },
        error: (error) => {
          console.error('Error al obtener el producto:', error);
        },
      });
    });
  }
  activeSlideIndex = 0;

  setActiveSlide(index: number): void {
    this.activeSlideIndex = index;
    const carouselElement: any = document.querySelector('#carouselExample');
    const carousel = new bootstrap.Carousel(carouselElement);
    carousel.to(index);
  }
  findUniqueColors(): void {
    if (!this.product || !this.product.variants) {
      return;
    }

    const uniqueColorsMap = new Map<string, { sizes: string[], url: string }>();

    this.product.variants.forEach(variant => {
      if (variant.colorSet && variant.size) {
        const colorName = variant.colorSet.name;
        if (!uniqueColorsMap.has(colorName)) {
          uniqueColorsMap.set(colorName, {
            sizes: [],
            url: variant.colorSet.url || ''  // Set URL if available
          });
        }
        uniqueColorsMap.get(colorName)?.sizes.push(variant.size);
      }
    });

    this.uniqueColors = Array.from(uniqueColorsMap.entries()).map(([name, details]) => ({
      name,
      sizes: details.sizes,
      url: details.url
    }));
  }

  // getUniqueImages(): string[] {
  //   if (!this.product || !this.product.images) {
  //     return [];
  //   }
  //
  //   const imageSet = new Set<string>();
  //
  //   this.product.images.forEach(image => {
  //     if (image.imgMin) {
  //       imageSet.add(image.imgMin);
  //     }
  //     if (image.imgMax) {
  //       imageSet.add(image.imgMax);
  //     }
  //   });
  //
  //   return Array.from(imageSet);
  // }


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
