import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UsersService } from '../../services/users.service';
import { AuthService } from '@ecommerce/shared-auth';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-user-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserListComponent implements OnInit {
  private readonly usersService = inject(UsersService);
  readonly authService = inject(AuthService);

  readonly users = signal<User[]>([]);
  readonly loading = signal<boolean>(false);
  readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading.set(true);
    this.error.set(null);

    this.usersService.getAll().subscribe({
      next: (users) => {
        this.users.set(users);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set('Erro ao carregar usuários.');
        this.loading.set(false);
        console.error(err);
      },
    });
  }

  deleteUser(id: number): void {
    if (!confirm('Tem certeza que deseja excluir este usuário?')) return;

    this.usersService.delete(id).subscribe({
      next: () => {
        this.users.update((users) => users.filter((u) => u.id !== id));
      },
      error: (err) => {
        this.error.set('Erro ao excluir usuário.');
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
}