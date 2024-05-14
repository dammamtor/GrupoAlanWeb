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

  //ELEMENTOS DE TRABAJO
  irASearch(searchTerm: string): void {
    this.ruta.navigate(['search', searchTerm]);
  }
}
