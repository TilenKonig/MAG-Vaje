import { HttpClient, httpResource } from '@angular/common/http';
import { Component, TemplateRef, inject, signal } from '@angular/core';
import { FormField, form, min, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../core/auth.service';
import { RezervniDel } from '../../core/models';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const prazno = { sifra: '', naziv: '', cena: 0, zaloga: 0, minZaloga: 0 };

@Component({
  imports: [FormField, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './deli.html',
  styleUrl: './deli.scss',
})
export class Deli {
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  protected readonly jeVodja = inject(AuthService).jeVodja;
  protected readonly deli = httpResource<RezervniDel[]>(() => '/api/deli', { defaultValue: [] });

  private model = signal({ ...prazno });
  protected readonly obrazec = form(this.model, (pot) => {
    required(pot.sifra);
    required(pot.naziv);
    min(pot.cena, 0);
    min(pot.zaloga, 0);
    min(pot.minZaloga, 0);
  });

  protected dodaj(predloga: TemplateRef<unknown>) {
    odpriObrazec(this.dialog, {
      naslov: 'Nov rezervni del',
      predloga,
      veljaven: () => !this.obrazec().invalid(),
      shrani: () => this.http.post('/api/deli', this.model()),
    }).subscribe((shranjeno) => {
      this.obrazec().reset({ ...prazno });
      if (shranjeno) this.deli.reload();
    });
  }

  protected izbrisi(del: RezervniDel) {
    this.http.delete(`/api/deli/${del.id}`).subscribe(() => this.deli.reload());
  }
}
