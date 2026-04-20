import { Injectable, signal, computed, inject } from '@angular/core';
import Keycloak from 'keycloak-js';

export interface AuthUser {
  id: string;
  username: string;
  email: string;
  firstName?: string;
  lastName?: string;
}

@Injectable({providedIn: 'root'})
export class AuthService {
  private keycloak = new Keycloak({
    url: 'http://192.168.68.112:8280',
    realm: 'ecommerce',
    clientId: 'frontend-shell',
  });

  private _isLoggedIn = signal<boolean>(false);
  private _user = signal<AuthUser | null>(null);
  private _roles = signal<string[]>([]);
  private _token = signal<string | null>(null);

  readonly isLoggedIn = this._isLoggedIn.asReadonly();
  readonly currentUser = this._user.asReadonly();
  readonly roles = this._roles.asReadonly();
  readonly token = this._token.asReadonly();

  readonly isAdmin = computed(() =>
    this._roles().includes('admin'));

  async init(): Promise<void> {
    const authenticated = await this.keycloak.init({
      onLoad: 'check-sso',
      checkLoginIframe: false,
    });

    this._isLoggedIn.set(authenticated);

    if (authenticated) {
      this._token.set(this.keycloak.token ?? null);
      this._roles.set(this.keycloak.realmAccess?.roles ?? []);

      const profile = await this.keycloak.loadUserProfile();
      this._user.set({
        id: profile.id ?? '',
        username: profile.username ?? '',
        email: profile.email ?? '',
        firstName: profile.firstName,
        lastName: profile.lastName,
      });
    }
  }

  async login(): Promise<void> {
    await this.keycloak.login();
  }

  async logout(): Promise<void> {
    await this.keycloak.logout();
  }

  async refreshToken(): Promise<void> {
    await this.keycloak.updateToken(30);
    this._token.set(this.keycloak.token ?? null);
  }

  hasRole(role: string): boolean {
    return this._roles().includes(role);
  }

}
