import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { BotellasComponent } from './components/botellas/botellas.component';
import { ProductoComponent } from './components/producto/producto.component';
import { SearchComponent } from './components/search/search.component';
import { TypesComponent } from './components/types/types.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

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
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'abanicos',
    component: BotellasComponent,
  },
  {
    path: 'botellas',
    component: BotellasComponent,
  },
  {
    path: 'boligrafos',
    component: BotellasComponent,
  },
  {
    path: 'bolsas',
    component: BotellasComponent,
  },
  {
    path: 'camisetas',
    component: BotellasComponent,
  },
  {
    path: 'gorras',
    component: BotellasComponent,
  },
  {
    path: 'libretas',
    component: BotellasComponent,
  },
  {
    path: 'mochilas',
    component: BotellasComponent,
  },
  {
    path: 'polos',
    component: BotellasComponent,
  },
  {
    path: 'ropaTrabajo',
    component: BotellasComponent,
  },
  {
    path: 'producto',
    component: ProductoComponent,
  },
  {
    path: 'search/:s',
    component: SearchComponent,
  },
  {
    path: 'types/:t',
    component: TypesComponent,
  },
];
