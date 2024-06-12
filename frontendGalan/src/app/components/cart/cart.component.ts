import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/Product';
import { CartService } from '../../services/cart.service';
import { HeaderComponent } from '../header/header.component';
import { Router, RouterLink } from '@angular/router';
import { Variants } from '../../models/Variants';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, HeaderComponent, RouterLink],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent implements OnInit {
  cart: { product: Product; variant: Variants; quantity: number }[] = [];
  totalPrice: number = 0;

  constructor(private cartService: CartService, private ruta: Router) {}

  ngOnInit(): void {
    this.cartService.cart$.subscribe((items) => {
      this.cart = items;
      this.calculateTotalPrice();
    });
  }

  removeFromCart(productId: number, variantId: number) {
    this.cartService.removeFromCart(productId, variantId);
    this.calculateTotalPrice();
  }

  updateQuantity(productId: number, variantId: number, quantity: number) {
    this.cartService.updateQuantity(productId, variantId, quantity);
    this.calculateTotalPrice();
  }

  clearCart() {
    this.cartService.clearCart();
    this.totalPrice = 0;
  }

  calculateTotalPrice() {
    this.totalPrice = this.cart.reduce(
      (acc, item) => acc + parseFloat(item.product.price) * item.quantity,
      0
    );
  }

  irAHome() {
    this.ruta.navigate(['home']);
  }
}
