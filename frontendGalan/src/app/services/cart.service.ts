import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from '../models/Product';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Variants } from '../models/Variants';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cart = new BehaviorSubject<
    { product: Product; variant: Variants; quantity: number }[]
  >([]);
  cart$ = this.cart.asObservable();

  constructor(private snackBar: MatSnackBar) {}

  addToCart(product: Product, variant: Variants) {
    const currentCart = this.cart.value;
    const existingProductIndex = currentCart.findIndex(
      (item) =>
        item.product.productId === product.productId &&
        item.variant.variantId === variant.variantId
    );

    if (existingProductIndex !== -1) {
      if (
        variant.stock &&
        currentCart[existingProductIndex].quantity < variant.stock.stock
      ) {
        currentCart[existingProductIndex].quantity += 1;
      } else {
        this.snackBar.open('No hay suficiente stock disponible!', 'Cerrar', {
          duration: 3000,
          verticalPosition: 'top',
          horizontalPosition: 'center',
        });
        return;
      }
    } else {
      currentCart.push({ product, variant, quantity: 1 });
    }

    this.cart.next(currentCart);
    this.snackBar.open('Producto añadido al carrito!', 'Cerrar', {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
      panelClass: ['custom-snack-bar'], // Utiliza la clase CSS personalizada
    });
  }

  removeFromCart(productId: number, variantId: number) {
    const currentCart = this.cart.value.filter(
      (item) =>
        !(
          item.product.productId === productId &&
          item.variant.variantId === variantId
        )
    );
    this.cart.next(currentCart);
  }

  updateQuantity(productId: number, variantId: number, quantity: number) {
    const currentCart = this.cart.value;
    const productIndex = currentCart.findIndex(
      (item) =>
        item.product.productId === productId &&
        item.variant.variantId === variantId
    );

    if (productIndex !== -1) {
      const variant = currentCart[productIndex].variant;
      if (variant.stock && quantity > variant.stock.stock) {
        this.snackBar.open('No hay suficiente stock disponible!', 'Cerrar', {
          duration: 3000,
          verticalPosition: 'top',
          horizontalPosition: 'center',
        });
      } else {
        currentCart[productIndex].quantity = quantity;
        if (currentCart[productIndex].quantity <= 0) {
          currentCart.splice(productIndex, 1);
        }
        this.cart.next(currentCart);
      }
    }
  }

  getCart() {
    return this.cart.value;
  }

  clearCart() {
    this.cart.next([]);
  }
}
