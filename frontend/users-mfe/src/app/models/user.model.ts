export interface User {
  id: number;
  name: string;
  email: string;
  active: boolean;
  createdAt: string;
}

export interface CreateUserRequest {
  name: string;
  email: string;
}