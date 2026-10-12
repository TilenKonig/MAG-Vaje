import { HttpClient } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { tap } from 'rxjs';
import { Vloga } from './models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  readonly token = signal(localStorage.getItem('token'));

  // vloga je v sredini žetona (JWT = glava.podatki.podpis, podatki so base64url)
  readonly vloga = computed(() => {
    const token = this.token();
    if (!token) return null;
    const podatki = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
    return JSON.parse(atob(podatki)).vloga as Vloga;
  });

  readonly jeVodja = computed(() => this.vloga() === 'VODJA');
  readonly jeOsebje = computed(() => this.vloga() === 'VODJA' || this.vloga() === 'MEHANIK');

  prijava(email: string, geslo: string) {
    return this.http.post<{ token: string }>('/api/prijava', { email, geslo }).pipe(
      tap((odgovor) => {
        localStorage.setItem('token', odgovor.token);
        this.token.set(odgovor.token);
      }),
    );
  }

  odjava() {
    localStorage.removeItem('token');
    this.token.set(null);
    this.router.navigate(['/prijava']);
  }
}
