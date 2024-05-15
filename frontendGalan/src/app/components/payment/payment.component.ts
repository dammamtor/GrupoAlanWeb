import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [HeaderComponent, CommonModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css',
})
export class PaymentComponent {
  cartItems = [
    {
      id: 1,
      name: 'Camiseta básica',
      color: 'negro',
      size: 'L',
      price: 19.99,
      image: '/assets/camiseta.png',
    },
  ];
  shippingCost = 3.95;

  removeItem(index: number) {
    this.cartItems.splice(index, 1);
  }

  moveToFavorites(index: number) {
    // Implementar lógica para mover a favoritos
  }

  calculateSubtotal() {
    return this.cartItems.reduce((acc, item) => acc + item.price, 0);
  }

  calculateTotal() {
    return this.calculateSubtotal() + this.shippingCost;
  }

  startOrder() {
    // Implementar lógica para comenzar el pedido
  }
}
