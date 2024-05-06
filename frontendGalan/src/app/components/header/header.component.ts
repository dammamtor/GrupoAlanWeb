import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  public botellasList: any[] = [];
  constructor(private ruta: Router) {}

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
}