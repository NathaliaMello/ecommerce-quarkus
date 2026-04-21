import { loadRemoteModule } from '@angular-architects/native-federation';
import { Route } from '@angular/router';

export const appRoutes: Route[] = [
    {
        path: '',
        redirectTo: 'users',
        pathMatch: 'full'
    },
    {
        path: 'users',
        loadComponent: () => 
            loadRemoteModule('users-mfe', './Component').then(
                (m) => m.App
            ), 
    },
    {
        path: 'orders',
        loadComponent: () => 
            loadRemoteModule('orders-mfe', './Component').then(
                (m) => m.App
            ), 
    },
    {
        path: 'notifications',
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
