import { Component, TemplateRef, inject, signal } from '@angular/core';
import { FormField, email, form, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { UporabnikiService } from '../../core/api/uporabniki.service';
import { Uporabnik } from '../../core/models';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const prazno = { ime: '', priimek: '', email: '', geslo: '', telefon: '', vloga: 'STRANKA' };

@Component({
  imports: [FormField, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './uporabniki.html',
  styleUrl: './uporabniki.scss',
})
export class Uporabniki {
  private uporabnikiApi = inject(UporabnikiService);
  private dialog = inject(MatDialog);

  protected readonly vloge = ['STRANKA', 'MEHANIK', 'VODJA'];
  protected readonly uporabniki = this.uporabnikiApi.seznam();

  private model = signal({ ...prazno });
  protected readonly obrazec = form(this.model, (pot) => {
    required(pot.ime);
    required(pot.priimek);
    required(pot.email);
    email(pot.email);
    required(pot.geslo, { when: () => !this.urejanjeId() }); // pri urejanju prazno geslo ohrani staro
  });
  private urejanjeId = signal<string | null>(null);

  protected dodaj(predloga: TemplateRef<unknown>) {
    this.odpri(predloga);
  }

  protected uredi(predloga: TemplateRef<unknown>, u: Uporabnik) {
    this.odpri(predloga, u);
  }

  private odpri(predloga: TemplateRef<unknown>, u?: Uporabnik) {
    this.urejanjeId.set(u?.id ?? null);
    this.obrazec().reset(u ? { ...prazno, ...u, telefon: u.telefon ?? '' } : { ...prazno });
    odpriObrazec(this.dialog, {
      naslov: u ? 'Urejanje uporabnika' : 'Nov uporabnik',
      predloga,
      veljaven: () => !this.obrazec().invalid(),
      shrani: () => {
        const { geslo, ...telo } = this.model();
        const podatki = geslo ? { ...telo, geslo } : telo;
        return u ? this.uporabnikiApi.posodobi(u.id, podatki) : this.uporabnikiApi.ustvari(podatki);
      },
    }).subscribe((shranjeno) => {
      this.obrazec().reset({ ...prazno });
      if (shranjeno) this.uporabniki.reload();
    });
  }

  protected izbrisi(uporabnik: Uporabnik) {
    this.uporabnikiApi.izbrisi(uporabnik.id).subscribe(() => this.uporabniki.reload());
  }
}
