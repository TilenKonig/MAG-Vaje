import { Component, inject, signal } from '@angular/core';
import { FormField, FormRoot, form, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  imports: [FormRoot, FormField, MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule],
  templateUrl: './prijava.html',
  styleUrl: './prijava.scss',
})
export class Prijava {
  private auth = inject(AuthService);
  private router = inject(Router);

  private model = signal({ email: '', geslo: '' });

  protected readonly obrazec = form(
    this.model,
    (pot) => {
      required(pot.email);
      required(pot.geslo);
    },
    { submission: { action: async () => this.prijava() } },
  );

  private prijava() {
    const { email, geslo } = this.model();
    this.auth.prijava(email, geslo).subscribe(() => this.router.navigate(['/']));
  }
}
