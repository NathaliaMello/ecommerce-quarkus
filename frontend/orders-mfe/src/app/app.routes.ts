import { Route } from '@angular/router';
import { OrderListComponent } from './components/order-list/order-list';
import { OrderFormComponent } from './components/order-form/order-form';

export const appRoutes: Route[] = [
  {
    path: '',
    redirectTo: '/orders/list',
    pathMatch: 'full',
  },
  {
    path: 'list',
    component: OrderListComponent,
  },
  {
    path: 'new',
    component: OrderFormComponent,
  },
];