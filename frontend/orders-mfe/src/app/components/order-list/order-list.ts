import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '@ecommerce/shared-auth';

import { OrdersService } from '../../service/orders.service';
import { Order } from '../../model/order.model';
@Component({
  selector: 'app-order-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './order-list.html',
  styleUrl: './order-list.css',
})
export class OrderListComponent implements OnInit {
  private readonly ordersService = inject(OrdersService);
  readonly authService = inject(AuthService);

  readonly orders = signal<Order[]>([]);
  readonly loading = signal<boolean>(false);
  readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.loading.set(true);
    this.error.set(null);

    const user = this.authService.currentUser();
    const request$ = this.authService.isAdmin()
      ? this.ordersService.getAll()
      : this.ordersService.getByUserId(Number(user?.id));

    request$.subscribe({
      next: (orders) => {
        this.orders.set(orders);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set('Erro ao carregar pedidos.');
        this.loading.set(false);
        console.error(err);
      },
    });
  }

  formatDate(dateStr: string): string {
    return new Intl.DateTimeFormat('pt-BR', {
      timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone,
      dateStyle: 'short',
      timeStyle: 'short',
    }).format(new Date(dateStr));
  }

  formatStatus(status: string): string {
    const map: Record<string, string> = {
      PENDING: 'Pendente',
      CONFIRMED: 'Confirmado',
      CANCELLED: 'Cancelado',
    };
    return map[status] ?? status;
  }

  statusClass(status: string): string {
    const map: Record<string, string> = {
      PENDING: 'status-pending',
      CONFIRMED: 'status-confirmed',
      CANCELLED: 'status-cancelled',
    };
    return map[status] ?? '';
  }
}