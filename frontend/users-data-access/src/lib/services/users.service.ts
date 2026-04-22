import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../models/user.model";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class UsersService {

    private readonly http = inject(HttpClient);
    private readonly apiUrl = 'http://localhost:8080/api/users';

    getById(id: number): Observable<User> {
        return this.http.get<User>(`${this.apiUrl}/${id}`);
    }
    
    getMe(): Observable<User> {
        return this.http.get<User>(`${this.apiUrl}/me`);
    }

}