import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '@ecommerce/shared-auth';

export const authGuard: CanActivateFn = async (route, state) => {
  const authService = inject(AuthService);

  if (authService.isLoggedIn()) {
    return true;
  }

  await authService.login();
  return false;
};