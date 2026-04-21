import { Route } from '@angular/router';
import { UserListComponent } from './components/user-list/user-list';
import { UserFormComponent } from './components/user-form/user-form';
import { AuthService } from '@ecommerce/shared-auth';
import { inject } from '@angular/core';

const adminGuard = () => inject(AuthService).isAdmin();

export const appRoutes: Route[] = [
  {
    path: '',
    redirectTo: '/users/list',
    pathMatch: 'full',
  },
  {
    path: 'list',
    component: UserListComponent,
  },
  {
    path: 'new',
    component: UserFormComponent,
    canActivate: [adminGuard],
  },
];