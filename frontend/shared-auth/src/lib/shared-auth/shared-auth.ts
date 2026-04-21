import { Injectable, signal, computed } from '@angular/core';
import Keycloak from 'keycloak-js';

export interface AuthUser {
  id: string;
  username: string;
  email: string;
  firstName?: string;
  lastName?: string;
}

// Usa window como storage global — sobrevive a múltiplos bundles
function getKeycloak(): Keycloak | undefined {
  return (window as any).__keycloak_instance__;
}

function setKeycloak(kc: Keycloak): void {
  (window as any).__keycloak_instance__ = kc;
}

@Injectable({ providedIn: 'platform' })
export class AuthService {
  private _isLoggedIn = signal<boolean>(false);
  private _user = signal<AuthUser | null>(null);
  private _roles = signal<string[]>([]);
  private _token = signal<string | null>(null);

  readonly isLoggedIn = this._isLoggedIn.asReadonly();
  readonly currentUser = this._user.asReadonly();
  readonly roles = this._roles.asReadonly();
  readonly token = this._token.asReadonly();

  readonly isAdmin = computed(() =>
    this._roles().includes('admin')
  );

  async init(): Promise<void> {
    console.log('AuthService.init() called');

    if (getKeycloak()) {
      console.log('Keycloak already initialized, skipping');
      return;
    }

    const kc = new Keycloak({
      url: 'http://192.168.68.112:8280',
      realm: 'ecommerce',
      clientId: 'frontend-shell',
    });

    const authenticated = await kc.init({
      onLoad: 'check-sso',
      checkLoginIframe: false,
    });

    setKeycloak(kc);
    console.log('Keycloak init result:', authenticated);
    this._isLoggedIn.set(authenticated);

    if (authenticated) {
      this._token.set(kc.token ?? null);
      this._roles.set(kc.realmAccess?.roles ?? []);
      const profile = await kc.loadUserProfile();
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
    console.log('login called, keycloak:', getKeycloak());
    await getKeycloak()?.login();
  }

  async logout(): Promise<void> {
    await getKeycloak()?.logout();
  }

  async refreshToken(): Promise<void> {
    await getKeycloak()?.updateToken(30);
    this._token.set(getKeycloak()?.token ?? null);
  }

  hasRole(role: string): boolean {
    return this._roles().includes(role);
  }
}