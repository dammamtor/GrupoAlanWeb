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
  uniqueMarkingsWithTechniques: { image: string, height: number, width: number, techniques: { name: string, max_colors: string }[] }[] = [];

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
          this.groupMarkingsByImage();
        },
        error: (error) => {
          console.error("Error al obtener el producto:", error);
        }
      });
    });
  }

  groupMarkingsByImage() {
    const imageTechniqueMap = new Map<string, { height: number, width: number, techniques: { name: string, max_colors: string }[] }>();

    if (this.product && this.product.markings) {
      this.product.markings.forEach(marking => {
        const technique = {
          name: marking.markingTechniques.name,
          max_colors: marking.max_colors
        };

        if (imageTechniqueMap.has(marking.area_img)) {
          imageTechniqueMap.get(marking.area_img)?.techniques.push(technique);
        } else {
          imageTechniqueMap.set(marking.area_img, {
            height: marking.height,
            width: marking.width,
            techniques: [technique]
          });
        }
      });

      console.log("Mapa de imágenes, tamaños y técnicas:", imageTechniqueMap);

      this.uniqueMarkingsWithTechniques = Array.from(imageTechniqueMap, ([image, data]) => ({
        image,
        height: data.height,
        width: data.width,
        techniques: data.techniques
      }));

      console.log("Marcajes únicos con tamaños y técnicas:", this.uniqueMarkingsWithTechniques);
    }
  }
}
