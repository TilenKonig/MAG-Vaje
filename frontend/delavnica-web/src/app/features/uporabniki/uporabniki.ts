import { HttpClient, httpResource } from '@angular/common/http';
import { Component, TemplateRef, inject, signal } from '@angular/core';
import { FormField, email, form, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Uporabnik } from '../../core/models';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const prazno = { ime: '', priimek: '', email: '', geslo: '', telefon: '', vloga: 'STRANKA' };

@Component({
  imports: [FormField, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './uporabniki.html',
  styleUrl: './uporabniki.scss',
})
export class Uporabniki {
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  protected readonly vloge = ['STRANKA', 'MEHANIK', 'VODJA'];
  protected readonly uporabniki = httpResource<Uporabnik[]>(() => '/api/uporabniki', { defaultValue: [] });

  private model = signal({ ...prazno });
  protected readonly obrazec = form(this.model, (pot) => {
    required(pot.ime);
    required(pot.priimek);
    required(pot.email);
    email(pot.email);
    required(pot.geslo);
  });

  protected dodaj(predloga: TemplateRef<unknown>) {
    odpriObrazec(this.dialog, {
      naslov: 'Nov uporabnik',
      predloga,
      veljaven: () => !this.obrazec().invalid(),
      shrani: () => this.http.post('/api/uporabniki', this.model()),
    }).subscribe((shranjeno) => {
      this.obrazec().reset({ ...prazno });
      if (shranjeno) this.uporabniki.reload();
    });
  }

  protected izbrisi(uporabnik: Uporabnik) {
    this.http.delete(`/api/uporabniki/${uporabnik.id}`).subscribe(() => this.uporabniki.reload());
  }
}
