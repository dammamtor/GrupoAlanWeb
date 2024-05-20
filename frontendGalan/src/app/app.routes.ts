import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { BotellasComponent } from './components/botellas/botellas.component';
import { ProductoComponent } from './components/producto/producto.component';
import { SearchComponent } from './components/search/search.component';
import { TypesComponent } from './components/types/types.component';
import { PaymentComponent } from './components/payment/payment.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'lista-productos',
    component: BotellasComponent,
  },
  {
    path: "lista-productos/producto/ref/:ref",
    component: ProductoComponent
  },
  {
    path:'search/:s',
    component: SearchComponent
  },
  {
    path:"search/:s/producto/ref/:ref",
    component: ProductoComponent
  },
  {
    path: "types/:t",
    component: TypesComponent
  },
  {
    path:"types/:t/producto/ref/:ref",
    component: ProductoComponent
  },
  {
    path: "payments",
    component: PaymentComponent
  }
];
