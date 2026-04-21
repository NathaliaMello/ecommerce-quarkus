import { loadRemoteModule } from '@angular-architects/native-federation';
import { Route } from '@angular/router';
import { authGuard } from './guards/auth-guard';

export const appRoutes: Route[] = [
    {
        path: '',
        redirectTo: 'users',
        pathMatch: 'full'
    },
    {
        path: 'users',
        canActivate: [authGuard],
        loadChildren: () =>
            loadRemoteModule('users-mfe', './Routes').then((m) => m.appRoutes),
    },
    {
        path: 'orders',
        canActivate: [authGuard],
        loadComponent: () => 
            loadRemoteModule('orders-mfe', './Component').then(
                (m) => m.App
            ), 
    },
    {
        path: 'notifications',
        canActivate: [authGuard],
        loadComponent: () => 
            loadRemoteModule('notifications-mfe', './Component').then(
                (m) => m.App
            ), 
    },
    {
        path: '**',
        redirectTo: 'users'
    },
];
