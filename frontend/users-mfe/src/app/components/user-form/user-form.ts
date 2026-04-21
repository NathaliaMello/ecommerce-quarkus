import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UsersService } from '../../services/users.service';
import { CreateUserRequest } from '../../models/user.model';

@Component({
  selector: 'app-user-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './user-form.html',
  styleUrl: './user-form.css',
})
export class UserFormComponent {
  private readonly usersService = inject(UsersService);
  private readonly router = inject(Router);

  readonly loading = signal<boolean>(false);
  readonly error = signal<string | null>(null);

  formData: CreateUserRequest = {
    name: '',
    email: '',
  };

  submit(): void {
    this.loading.set(true);
    this.error.set(null);

    this.usersService.create(this.formData).subscribe({
      next: () => {
        this.router.navigateByUrl('/users/list');
      },
      error: (err) => {
        this.error.set('Erro ao criar usuário.');
        this.loading.set(false);
        console.error(err);
      },
    });
  }

  cancel(): void {
    this.router.navigateByUrl('/users/list');
  }
}