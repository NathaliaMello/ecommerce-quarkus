import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { OrdersService } from '../../service/orders.service';
import { CreateOrderRequest } from '../../model/order.model';
import { UsersService } from '@ecommerce/users-data-access';

@Component({
  selector: 'app-order-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './order-form.html',
  styleUrl: './order-form.css',
})
export class OrderFormComponent implements OnInit {
  private readonly ordersService = inject(OrdersService);
  private readonly usersService = inject(UsersService);
  private readonly router = inject(Router);

  private readonly idempotencyKey = crypto.randomUUID();

  readonly loading = signal<boolean>(false);
  readonly loadingUser = signal<boolean>(true);
  readonly error = signal<string | null>(null);

  formData: CreateOrderRequest = {
    userId: 0,
    total: 0,
  };

  ngOnInit(): void {
    this.usersService.getMe().subscribe({
      next: (user) => {
        this.formData.userId = user.id;
        this.loadingUser.set(false);
      },
      error: () => {
        this.error.set('Erro ao carregar dados do usuário.');
        this.loadingUser.set(false);
      },
    });
  }

  submit(): void {
    this.loading.set(true);
    this.error.set(null);

    this.ordersService.create(this.formData, this.idempotencyKey).subscribe({
      next: () => {
        this.router.navigateByUrl('/orders/list');
      },
      error: (err) => {
        this.error.set(this.parseError(err));
        this.loading.set(false);
      },
    });
  }

  cancel(): void {
    this.router.navigateByUrl('/orders/list');
  }

  private parseError(err: any): string {
    const code = err?.error?.code;
    const messages: Record<string, string> = {
      USER_INACTIVE: 'Usuário inativo. Não é possível criar pedidos.',
      USER_NOT_FOUND: 'Usuário não encontrado.',
      USER_SERVICE_UNAVAILABLE: 'Serviço temporariamente indisponível. Tente novamente.',
      MISSING_IDEMPOTENCY_KEY: 'Erro interno. Recarregue a página e tente novamente.',
    };
    return messages[code] ?? 'Erro ao criar pedido.';
  }
}