import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';
import { provideOAuthClient } from 'angular-oauth2-oidc';
import { AdminService } from './services/admin.service';
import { AuthService } from './services/auth.service';
import { AuthGuard } from './services/guards/auth.guard';
import { UserServiceService } from './services/user-service.service';
import { JwtInterceptor } from './services/interceptors/jtw.interceptors';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideOAuthClient(),
    AuthService,
    AdminService,
    UserServiceService,
    AuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }, provideAnimationsAsync(),
  ],
};
