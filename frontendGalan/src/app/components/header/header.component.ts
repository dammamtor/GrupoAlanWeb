import { Component, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthGoogleService } from '../../services/auth-google.service';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/Product';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, FormsModule, ReactiveFormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  private ruta = inject(Router);
  public botellasList: any[] = [];
  private authService = inject(AuthGoogleService);
  profile: any;

  // inicio LOGIN
  signInWithGoogle() {
    this.authService.login();
  }

  ngOnInit(): void {
    this.showData();
  }

  showData() {
    this.profile = this.authService.getProfile();
  }

  logOut() {
    this.authService.logout();
    this.ruta.navigate(['/login']);
  }
  // fin LOGIN

  navegateLogin() {
    this.ruta.navigate(['login']);
  }
  navegatePayment() {
    this.ruta.navigate(['payments']);
  }
  navegateAbanicos() {
    this.ruta.navigate(['types', 'Abanico']);
  }

  navegateBotellas() {
    this.ruta.navigate(['types', 'Botella']);
  }

  navegateBoligrafos() {
    this.ruta.navigate(['types', 'Bolígrafo']);
  }

  navegateBolsas() {
    this.ruta.navigate(['types', 'Bolsa']);
  }

  navegateCamisetas() {
    this.ruta.navigate(['types', 'Camiseta']);
  }

  navegateGorras() {
    this.ruta.navigate(['types', 'Gorra']);
  }

  navegateLibretas() {
    this.ruta.navigate(['types', 'Libreta']);
  }

  navegateMochilas() {
    this.ruta.navigate(['types', 'Mochila']);
  }

  navegatePolos() {
    this.ruta.navigate(['types', 'Polo']);
  }

  //ELEMENTOS DE TRABAJO
  irASearch(searchTerm: string): void {
    this.ruta.navigate(['search', searchTerm]);
  }

}
