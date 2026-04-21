export type OrderStatus = 'PENDING' | 'CONFIRMED' | 'CANCELLED';

export interface Order {
  id: number;
  userId: number;
  status: OrderStatus;
  total: number;
  createdAt: string;
  idempotencyKey: string;
}

export interface CreateOrderRequest {
  userId: number;
  total: number;
}