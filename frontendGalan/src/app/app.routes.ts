import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { BotellasComponent } from './components/botellas/botellas.component';
import { ProductoComponent } from './components/producto/producto.component';
import { SearchComponent } from './components/search/search.component';
import { TypesComponent } from './components/types/types.component';
import { PaymentComponent } from './components/payment/payment.component';
import { LoginComponent } from './components/crear-registrar-usuario/login/login.component';
import { RegisterComponent } from './components/crear-registrar-usuario/register/register.component';
import { VerifyUserComponent } from './components/crear-registrar-usuario/verify-user/verify-user.component';
import { AfterRegisterComponent } from './components/crear-registrar-usuario/after-register/after-register.component';
import { HomeAdminComponent } from './components/admin/home-admin/home-admin.component';
import { RegisterProfesionalComponent } from './components/crear-registrar-usuario/register-profesional/register-profesional.component';
import { AdminUsuariosComponent } from './components/admin/admin-usuarios/admin-usuarios.component';
import { AdminPedidosComponent } from './components/admin/admin-pedidos/admin-pedidos.component';
import { AdminDetallesPedidosComponent } from './components/admin/admin-detalles-pedidos/admin-detalles-pedidos.component';
import { HomeUserComponent } from './components/user/home-user/home-user.component';
import { UserEditprofileComponent } from './components/user/user-editprofile/user-editprofile.component';
import { UserNotificacionesComponent } from './components/user/user-notificaciones/user-notificaciones.component';
import { UserHistorialComponent } from './components/user/user-historial/user-historial.component';
import { UserHistorialDetallesComponent } from './components/user/user-historial-detalles/user-historial-detalles.component';
import { UserFavoritosComponent } from './components/user/user-favoritos/user-favoritos.component';
import { UserConfiguracionComponent } from './components/user/user-configuracion/user-configuracion.component';
import { AuthGuard } from './services/guards/auth.guard';
import { BusquedaAvanzadaComponent } from './components/busqueda-avanzada/busqueda-avanzada.component';
import { ProductosFiltradosComponent } from './components/productos-filtrados/productos-filtrados.component';
import { CartComponent } from './components/cart/cart.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'lista-productos/:page',
    component: BotellasComponent,
  },
  {
    path: 'lista-productos/producto/ref/:ref',
    component: ProductoComponent,
  },
  {
    path: 'search/:s',
    component: SearchComponent,
  },
  {
    path: 'search/:s/producto/ref/:ref',
    component: ProductoComponent,
  },
  {
    path: 'producto/:ref',
    component: ProductoComponent,
  },
  {
    path: 'types/:t',
    component: TypesComponent,
  },
  {
    path: 'types/:t/producto/ref/:ref',
    component: ProductoComponent,
  },
  {
    path: 'payments',
    component: PaymentComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register-user',
    component: RegisterComponent,
  },
  {
    path: 'register-professional-user',
    component: RegisterProfesionalComponent,
  },
  {
    path: 'carrito',
    component: CartComponent,
  },
  {
    path: 'verify/:token',
    component: VerifyUserComponent,
  },
  {
    path: 'correct-registration/:username',
    component: AfterRegisterComponent,
  },
  {
    path: 'admin/home',
    component: HomeAdminComponent,
  },
  {
    path: 'admin/users',
    component: AdminUsuariosComponent,
  },
  {
    path: 'admin/pedidos',
    component: AdminPedidosComponent,
  },
  {
    path: 'admin/detalles-pedido',
    component: AdminDetallesPedidosComponent,
  },
  {
    path: 'user/home',
    component: HomeUserComponent,
  },
  {
    path: 'user/edit-user',
    component: UserEditprofileComponent,
  },
  {
    path: 'user/notificaciones',
    component: UserNotificacionesComponent,
  },
  {
    path: 'user/historial',
    component: UserHistorialComponent,
  },
  {
    path: 'user/historial/pedido/id',
    component: UserHistorialDetallesComponent,
  },
  {
    path: 'user/favoritos',
    component: UserFavoritosComponent,
  },
  {
    path: 'user/configuracion',
    component: UserConfiguracionComponent,
  },
  {
    path: 'busqueda-avanzada',
    component: BusquedaAvanzadaComponent,
  },
  {
    path: 'busqueda-avanzada/productos-filtrados',
    component: ProductosFiltradosComponent,
  },
  {
    path: 'busqueda-avanzada/productos-filtrados/ref/:ref',
    component: ProductoComponent,
  },
];
