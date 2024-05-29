import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { BotellasComponent } from './components/botellas/botellas.component';
import { ProductoComponent } from './components/producto/producto.component';
import { SearchComponent } from './components/search/search.component';
import { TypesComponent } from './components/types/types.component';
import { PaymentComponent } from './components/payment/payment.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { VerifyUserComponent } from './components/verify-user/verify-user.component';
import { AfterRegisterComponent } from './components/after-register/after-register.component';

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
    path: 'search/:s',
    component: SearchComponent
  },
  {
    path: "search/:s/producto/ref/:ref",
    component: ProductoComponent
  },
  {
    path: "types/:t",
    component: TypesComponent
  },
  {
    path: "types/:t/producto/ref/:ref",
    component: ProductoComponent
  },
  {
    path: "payments",
    component: PaymentComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register-user",
    component: RegisterComponent
  },
  {
    path: 'verify/:token',
    component: VerifyUserComponent
  },
  {
    path: "correct-registration/:username",
    component: AfterRegisterComponent    
  }
];
