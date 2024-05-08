import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root',
})
export class AuthGoogleService {
  private oAuthService = inject(OAuthService);
  private router = inject(Router);

  constructor() {
    this.initConfiguration();
  }

  // Este método se utilizará para configurar todos los parámetros de configuración de nuestra aplicación;;;https://accounts.google.com
  initConfiguration() {
    const authConfig: AuthConfig = {
      issuer: 'https://accounts.google.com',
      strictDiscoveryDocumentValidation: false,
      clientId:
        '538057352730-gtf4lu59g5ol7sfjclku68shafvnnaek.apps.googleusercontent.com',
      redirectUri: window.location.origin + '/home',
      scope: 'openid profile email',
    };

    this.oAuthService.configure(authConfig);
    this.oAuthService.setupAutomaticSilentRefresh();
    this.oAuthService.loadDiscoveryDocumentAndTryLogin();
  }
  /**
   * Metodo de inicio de sesion:
   *  initImplicitFlow,este método inicia el flujo implícito del proceso de autenticación OAuth 2.0, que
   *  redirige al usuario a la página de inicio de sesión del proveedor de identidad para su autenticación.
   */
  login() {
    this.oAuthService.initImplicitFlow();
  }
  /**
   * Metodo para cerrar sesion:
   *  Logout()se utiliza para cerrar la sesión del usuario de la aplicación. Realiza dos acciones:
   *
   *  1.- Las llamadas revokeTokenAndLogout():a este método revocan el token de acceso y realizan el proceso de cierre de sesión con el proveedor de identidad.
   *  2.- Las llamadas logOut():a este método realizan operaciones adicionales de limpieza y cierre de sesión específicas del servicio OAuth.
   */
  logout() {
    this.oAuthService.revokeTokenAndLogout();
    this.oAuthService.logOut();
  }
  /**
   * METODO QUE DEVUELVE EL PERFIL:
   * Recupera las afirmaciones de identidad del usuario actualmente autenticado.
   * @returns Llama a getIdentityClaims(), un método que devuelve
   * un objeto que contiene las afirmaciones de identidad del usuario, como nombre de usuario, correo electrónico y otra información de perfil.
   */
  getProfile() {
    return this.oAuthService.getIdentityClaims();
  }
  /**
   * Metodo para obtener el token:
   * Es responsable de recuperar el token de acceso del usuario actualmente autenticado.
   * @returns  Llama al getAccessToken()que devuelve el token de acceso como una cadena.
   */
  getToken() {
    return this.oAuthService.getAccessToken();
  }
}
